# Introduzione

Lo scopo di questo progetto è l'implementazione di un compilatore per il linguaggio di alto livello *Linf* (*Linf Is Not Fool*) derivato da *FOOL* e della *Linf Virtual Machine*, la macchina virtuale che esegue il bytecode generato dal compilatore.

Il progetto è realizzato in linguaggio *Java* e si basa sul framework per la generazione di parser *ANTLR*, nella versione `4.7.2`.

## Scelte progettuali

- Scope statico
- Controllo dei tipi statico
- Ricorsione diretta e mutua ricorsione (sono implementati i prototipi di funzione)
- Tipi comportamentali

## Grammatica

La grammatica di *Linf* è definita nel file `main/linf/parser/ComplexStaticAnalysis.g4`. La suddetta grammatica, in forma *BNF*, ci è stata fornita direttamente dal professore e non è stata in alcun modo modificata.

Al contrario, il linguaggio completo per il bytecode non ci è stato fornito, ma è stato definito ex novo all'interno di `main/lvm/parser/LVM.g4`.



# Compilatore Linf

Il compilatore si avvale del visitor `LinfVisitorImpl.java` per scorrere l'albero di parsing e costruire l'albero di sintassi astratta formato dai nodi di vario tipo.

L'analisi statica effettuata dal compilatore si compone di tre fasi:

1. **Analisi lessicale**: il compilatore controlla la sintassi del programma
2. **Analisi semantica**:
3. **Controllo dei tipi**:

Si noti che il fallimento di una fase implica l'interruzione dell'esecuzione. Infine il compilatore visita l'albero una quarta volta generando il bytecode di ogni nodo.

## Analisi lessicale



## Analisi semantica

In questa fase si visita l'albero di sintassi astratto costruito dal parser e si cercano eventuali errori semantici.


Per ogni nuovo identificatore che viene dichiarato, si aggiunge una entry corrispondente nell'environment (implementato tramite lista di tabelle hash.

Ogni entry della tabella dei simboli è formata da:
- il suo nesting level
- il suo offset all'interno del nesting level
- il tipo relativo al nodo a cui fa riferimento
- il nome dell'id

### Errori semantici

Gli errori semantici che possono essere catturati sono:

- `DoubleDeclaration`: una variabile può essere dichiarata, nello stesso scope, al massimo una volta
- `FunctionNameShadowing`: l'id della funzione e gli id dei parametri formali devono essere diversi
- `IllegalDeletion`: un id può essere cancellato al più una volta
- `SymbolUsedAsFunction`: un id non può essere usato come simbolo di funzione se è di tipo diverso
- `UnboundSymbol`: l'id non è presente (o è stato cancellato) nello scope attuale
- `VarParameterDoubleDeletion`: cancellazione multipla di un parametro attuale passato per riferimento ad una funzione
- `WrongParameterNumber`: numero dei parametri formali diverso dal numero dei parametri attuali


## Controllo dei tipi

In questa fase viene effettuato il controllo dei tipi e delle operazioni consentite tra di essi. *Linf* possiede 3 diversi tipi:

1. Intero
2. Booleano
3. Funzione

*Linf* non possiede sottotipi nè valori di ritorno per le funzioni, il che semplifica sensibilmente il controllo dei tipi che si riduce al verificare che le espressioni siano ben formate, i tipi dei due lati dell'assegnamento coincidano e gli argomenti passati ad una funzione corrispondano con la firma della funzione.

### Tipi comportamentali

La definizione di tipo comportamentale utilizzata nel compilatore ricalca direttamente quella fornita a lezione:

1. Una locazione di memoria non può essere acceduta nello stesso blocco sia in scrittura/lettura che in cancellazione
2. Le locazioni di memoria cancellate dai due branch di un *if-then-else* devono essere le stesse

In *Linf* dunque il comportamento di un blocco è stato definito come l'insieme dei comportamenti degli statement contenuti in quel blocco. Più formalmente possiamo definire il comportamento di un blocco $B$ come la coppia di insiemi di identificatori

$$RW = \mathop{\bigcup}_{s \in B} RW_s$$

$$DEL = \mathop{\bigcup}_{s \in B} DEL_s$$

Gli insiemi $RW$ e $DEL$ contengono rispettivamente tutti gli identificatori acceduti **non locali** in lettura/scrittura e tutti gli identificatori **non locali** cancellati (da un comando `delete` o da una chiamata di funzione) all'interno di un blocco.

Ogni blocco al termine del proprio controllo di tipo confronta propri insiemi $RW$ e $DEL$ per verificare la regola 1 e nel caso incotri un conflitto lancia l'errore `IncompatibleBehaviourError`.

Per quanto riguarda l'*if-then-else*, in fase di controllo di tipo il compilatore verifica che gli insiemi $DEL$ del ramo *then* e del ramo *else* siano uguali.

Il comportamento delle funzioni è simile. Quando una funzione viene dichiarata, si inseriscono nell'environment oltre al tipo della funzione anche gli insiemi $RW$ e $DEL$ della funzione, successivamente, quando la funzione viene invocata, si controlla che gli identificatori cancellati dalla funzione (compresi i parametri passati per riferimento) non siano già presenti nell'insieme $DEL$ del blocco chiamante e nel caso viene sollevato l'errore `DoubleDeletionError`. Non è possibile cancellare l'identificatore di una funzione nel corpo stesso della funzione.

### Errori di tipo
Gli errori di tipo che possono essere catturati sono:
- `DoubleDeletion`: un id viene cancellato due o più volte
- `IncompatibleBehaviour`: un identificatore a cui si accede in lettura e/o scrittura, è stata cancellata
- `IncompatibleTypes`: il tipo di lhs e rhs non coincidono
- `MismatchedPrototype`: il tipo dei parametri formali non corrisponde con quelli attuali
- `ReferenceParameter`: il parametro attuale è un'espressione invece che l'identificatore di una variabile, mentre il parametro formale è l'identificatore di una variabile passata per riferimento
- `UnbalancedDeletionBehaviour`: i branch dell'if-then-else sono sbilanciati per quanto riguarda la delete
- `WrongParameterType`: il tipo dei parametri formali della funzione non corrisponde con quello dei parametri attuali

## Generazione di codice

Anche la generazione di codice beneficia in maniera non indifferente dell'assenza di sottotipaggio nel linguaggio *Linf*: tutti i tipi di dato primitivi hanno la stessa dimensione (4 byte) che è pari alla word della LVM.

La generazione di codice del compilatore *Linf* rispetta l'invariante che l'esecuzione del codice generato debba lasciare invariato il valore dello *stack pointer* e del *frame pointer*.

Ogni blocco riserva le prime tre locazioni di memoria per:

1. **Control link**: detto anche *dynamic link* ovvero il valore del *frame pointer* nell'ambiente chiamante
2. **Access link**: detto anche *static link* ovvero il valore del *frame pointer* nell'ambiente in cui è stata definito il blocco
3. **Indirizzo di ritorno**: l'indirizzo dell'istruzione successiva alla chiamata della funzione

dunque il *frame pointer* all'interno di un blocco punta sempre alla prima locazione di memoria dopo l'indirizzo di ritorno.

**N.B.** nel caso di blocchi annidati o di funzioni chiamate nello stesso blocco in cui sono dichiarate, il control link e l'access link possono coincidere.

### Identificatori di variabile

Se l'identificatore è stato definito nel blocco in cui viene utilizzato la generazione di codice si riduce ad una istruzione che accede ad un determinato offset dal valore del frame pointer.

Quando una funzione, mentre genera codice per i suoi argomenti, deve generare il codice di un parametro passato per riferimento, invece che pusharne il valore, **risale all'indirizzo** della locazione di memoria puntata dal parametro attuale (che può essere a sua volta un parametro per riferimento di un'eventuale funzione chiamante) e lo pusha al posto del valore dell'identificatore.

Nel caso di un accesso ad identificatori **non locali** si presentano due possibilità: l'identificatore è un parametro passato per riferimento e quindi rappresenta una cella di memoria che può essere acceduta solo risalendo il control link fino a giungere al blocco della chiamata che contiene l'indirizzo della locazione riferita, oppure l'identificatore è una variabile libera e quindi va cercata a partire dall'ambiente in cui è stato definito il blocco seguendo prima l'access link ed eventualmente il control link se l'identificatore non è definito nello stesso ambiente in cui è definito il blocco.


# Linf Virtual Machine

Anche in questo caso ci si avvale del visitor `LVMVisitorImpl.java` per visitare il bytecode.



# Conclusioni


# Test

## Test 1

```Javascript
int x = 1;

f(int y){
    if (y == 0) then {      print(x);
    } else {
        f(y-1);
    }

f(54);
```
Restituisce 1.

## Test 2

```Javascript
int u = 1 ;

f(var int x, int n){

	if (n == 0) then { print(x); }

	else { int y = x*n; f(y,n-1); }

	delete x ;

}

f(u,6) ;
```
Restituisce l'errore `IncompatibleBehaviour` in quanto la delete cancella la var x del chiamante visto che è passata per riferimento.


## Test 3

```Javascript
f(int m, int n){

	if (m>n) { print(m+n) ;}

	else { int x = 1; f(m+1,n+1); }

}

f(5,4);

```
Restituisce 9.


## Test 4

```
f(int m, int n){

	if (m>n) { print(m+n);}

	else { int x = 1; f(m+1,n+1); }

}

f(4,5) ;

```
Restituisce stack overflow.

# Setup

## Aggiornamento ANTLR

> **N.B.** È necessario usare la versione ANTLR `4.7.2.`

### Eclipse

1. Lanciare Eclipse ed aprire il progetto desiderato
2. Effettuare un click **destro** sul progetto nella vista ad albero e selezionare l'opzione *properties*
3. Selezionare *Java Build Path* sulla sinistra, e quindi aprire la tab *Libraries*
4. Selezionare il comando *Add External JARs* sulla destra e infine selezionare il `.jar` di ANTLR presente in `lib/`

### Intellij IDEA

1. Effettuare un click **destro** sul progetto nel pannello *Project Explorer*
2. Selezionare *Open Module Settings* e aprire la tab *Dependencies*
3. Selezionare il comando *Add* (icona a forma di +) e quindi selezionare la voce *JARs or directories*
4. Selezionare il jar presente in `lib/` contenente la versione `4.7.2` di ANTLR

# References


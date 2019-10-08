# Linguaggio Linf - Compilatori e Interpreti 2018/2019

## Setup

### Aggiornamento ANTLR

> **N.B.** È necessario usare la versione ANTLR `4.7.2.`

#### Eclipse

1. Lanciare Eclipse ed aprire il progetto desiderato
2. Effettuare un click **destro** sul progetto nella vista ad albero e selezionare l'opzione *properties*
3. Selezionare *Java Build Path* sulla sinistra, e quindi aprire la tab *Libraries*
4. Selezionare il comando *Add External JARs* sulla destra e infine selezionare il `.jar` di ANTLR presente in `lib/`

#### Intellij IDEA

1. Effettuare un click **destro** sul progetto nel pannello *Project Explorer*
2. Selezionare *Open Module Settings* e aprire la tab *Dependencies*
3. Selezionare il comando *Add* (icona a forma di +) e quindi selezionare la voce *JARs or directories*
4. Selezionare il jar presente in `lib/` contenente la versione `4.7.2` di ANTLR


## Introduzione

Lo scopo di questo progetto è l'implementazione di un compilatore per il linguaggio di alto livello `Linf` (*Linf Is Not Fool*) derivato da *FOOL* e della *Linf Virtual Machine*, la macchina virtuale che esegue il bytecode generato dal compilatore.

Il progetto si basa sul framework per la generazione di parser *ANTLR*, nella versione `4.7.2`.

### Scelte progettuali

- Scope statico
- Controllo dei tipi statico
- Ricorsione diretta e mutua ricorsione (sono implementati i prototipi di funzione)
- Tipi comportamentali

### Sintassi

La sintassi di `Linf` è definita dalla grammatica presente in `main/linf/parser/ComplexStaticAnalysis.g4` che è composta dalle regole del parser e del lexer.

La suddetta grammatica, in forma *BNF*, ci è stata fornita direttamente dal professore e non è stata in alcun modo modificata.

Al contrario, il linguaggio completo per il bytecode non ci è stato fornito, ma è stato definito ex novo all'interno di `main/lvm/parser/LVM.g4`.

### Struttura

 Il progetto si divide fondamentalmente in due parti:

 1. `Linf`: il compilatore che traduce il linguaggio `Linf` in bytecode
 2. `Lvm` (Linf Virtual Machine): la macchina virtuale a registri

## Compilatore Linf

Il compilatore è contenuto nella cartella `src/main/java/linf`. Al suo interno sono presenti le sottocartelle:
- error/: per la definizione degli errori, rispettivamente, semantici e di tipo
- expression/: per l'implementazione delle espressioni definite dalla grammatica
- statement/: per l'implementazione degli statement definiti nella grammatica
- type/: per la definizione dei tipi in accordo con la grammatica
- utils/: che contiene alcune classi utilizzate in vari modi all'interno del progetto

Il compilatore si avvale del visitor `LinfVisitorImpl.java`, offerto da ANTLR, per scorrere l'albero di parsing e costruire l'albero di sintassi astratta formato dai nodi di vario tipo.

L'analisi statica effettuata dal compilatore si compone di tre fasi:

1. **Analisi Lessicale**: il compilatore controlla la sintassi del programma ed emette

### Analisi lessicale



### Analisi semantica

In questa fase si visita l'albero di sintassi astratto costruito dal parser e si cercano eventuali errori semantici.

Gli errori semantici che possono essere catturati sono:
- DoubleDeclaration: una variabile può essere dichiarata, nello stesso scope, al massimo una volta
- FunctionNameShadowing: l'id della funzione e gli id dei parametri formali devono essere diversi
- IllegalDeletion: un id può essere cancellato al più una volta
- SymbolUsedAsFunction: un id non può essere usato come simbolo di funzione se è di tipo diverso
- UnboundSymbol: l'id non è presente nello scope attuale (ad esempio, la cancellazione dell'id di una funzione, prima della sua chiamata o la cancellazione di una variabile che non è presente nello scope)  
- VarParameterDoubleDeletion: cancellazione multipla di un parametro attuale di una funzione (ad esempio quando viene passato per riferimento)
- WrongParameterNumber: numero dei parametri formali diverso dal numero dei parametri attuali 

Lo scope e il sistema di tipaggio sono entrambi statici, per cui è necessario dichiarare ogni variabile e ogni funzione prima dell'utilizzo.
Per ogni nuovo id che viene dichiarato, si aggiunge la entry corrispondente nella tabella dei simboli che è formata da una lista di tabelle hash.

Ogni entry (STentry) è formata da:
- il suo nesting level
- il suo offset all'interno del nesting level
- il tipo relativo al nodo a cui fa riferimento 
- il nome dell'id

### Controllo dei tipi

In questa fase viene effettuato il controllo dei tipi e delle operazioni consentite tra di essi.

`Linf` possiede 3 diversi tipi:
- Int
- Bool
- Fun

Viene inoltre supportata una forma di controllo dei tipi comportamentali, all'interno delle funzioni e del costrutto if-then-else.

Per quanto riguarda l'if-then-else, vengono mantenuti in due diversi insiemi gli identificatori a cui si accede in lettura e/o scrittura e quelli cancellati, sia per il ramo then che per l'else.
Successivamente si controlla se i due rami sono in qualche modo sbilanciati e in quel caso si restituisce il tipo di errore appropriato.

Il comportamente per le funzioni è simile. Quando una funzione viene dichiarata, si inserisco all'interno dei due insiemi rwIDs e delIDs gli identificatori delle variabili, rispettivamente, lette e/o scritte e cancellate. Successivamente, quando la funzione viene invocata si fanno tutti i controlli sui parametri, sugli identificatori cancellati, ecc.
Non è possibile cancellare l'identificatore di una funzione nel corpo stesso della funzione.

Gli errori di tipo che possono essere catturati sono:
- DoubleDeletion: un id viene cancellato due o più volte
- IncompatibleBehaviour:  un identificatore a cui si accede in lettura e/o scrittura, è stata cancellata
- IncompatibleTypes: il tipo di lhs e rhs non coincidono
- MismatchedPrototype: il tipo dei parametri formali non corrisponde con quelli attuali 
- ReferenceParameter: il parametro attuale è un'espressione invece che l'identificatore di una variabile, mentre il parametro formale è l'identificatore di una variabile passata per riferimento
- UnbalancedDeletionBehaviour: i branch dell'if-then-else sono sbilanciati per quanto riguarda la delete
- WrongParameterType: il tipo dei parametri formali della funzione non corrisponde con quello dei parametri attuali   

### Generazione di codice


## Linf Virtual Machine

L'interpete è contenuto all'interno della cartella `src/main/java/lvm`. Al suo interno sono presenti le sottocartelle:
- error/: per la gestione degli errori a runtime
- utils/: che contiene alcune classi utilizzate in vari modi all'interno della `lvm`.

Anche in questo caso, come nel precedente, ci si avvale del visitor `LVMVisitorImpl.java` per visitare il bytecode.



## Conclusioni


## Test

### Test 1

```
int x = 1;

f(int y){ 
    if (y == 0) then {      print(x);
    } else { 
        f(y-1); 
    }

f(54);
```
Restituisce 1.


### Test 2

```
int u = 1 ;

f(var int x, int n){ 

	if (n == 0) then { print(x); }

	else { int y = x*n; f(y,n-1); }

	delete x ;

}

f(u,6) ;
```
Restituisce l'errore `IncompatibleBehaviour` in quanto la delete cancella la var x del chiamante visto che è passata per riferimento.


### Test 3

```
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


## Autori

Filippo Bartolini, matricola n°
Giacomo Leidi, matricola n°


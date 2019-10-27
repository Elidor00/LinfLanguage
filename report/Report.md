# Introduzione

Lo scopo di questo progetto è l'implementazione di un compilatore per il linguaggio di alto livello *Linf* (*Linf Is Not Fool*) derivato da *FOOL* e della *Linf Virtual Machine*, la macchina virtuale che esegue il bytecode generato dal compilatore.

Il progetto è realizzato in linguaggio *Java* e si basa sul framework per la generazione di parser *ANTLR*, nella versione `4.7.2`.

## Setup

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


## Scelte progettuali

- Scope statico
- Controllo dei tipi statico
- Ricorsione diretta e mutua ricorsione (sono implementati i prototipi di funzione)
- Tipi comportamentali

## Grammatica

La grammatica di *Linf* è definita nel file `main/linf/parser/ComplexStaticAnalysis.g4`. La suddetta grammatica, in forma *BNF*, ci è stata fornita direttamente dal professore.

Al contrario, il linguaggio completo per il bytecode non ci è stato fornito, ma è stato definito ex novo all'interno di `main/lvm/parser/LVM.g4`.

### Prototipi di funzione

Al fine di supportare la mutua ricorsione si è fatto ricorso ad uno dei metodi più usati storicamente dai linguaggi a scope statico (ad esempio C) ovvero i prototipi di funzione.

In *Linf* il prototipo di funzione segue la seguente sintassi

```ANTLR
prototype : ID '(' ( parameter ( ',' parameter)* )? ');'
```

ed ha lo scopo di inserire la firma di una funzione $f$ nell'ambiente senza legarla ad un blocco, permettendo quindi l'analisi statica di un blocco in cui $f$ viene chiamata prima che sia dichiarata come nelle funzioni mutualmente ricorsive.

Prendiamo in considerazione le funzioni `isEven` e `isOdd`:

```Javascript
isEven(int x, var bool out) {
    if (x == 0) then {
        out = true;
    } else {
        isOdd(x - 1, out);
    }
}
```

```Javascript
isOdd(int x, var bool out) {
    if (x == 0) then {
        out = false;
    } else {
        isEven(x - 1, out);
    }
}
```

Supponiamo che il compilatore *Linf* stia eseguendo l'analisi statica del ramo *else* di una delle due funzioni per esempio `isEven`. Il compilatore non ha alcun modo di stabilire se la chiamata ad `isOdd` è ben formata o meno in quanto il tipo della funzione non è ancora stato inserito nell'ambiente e invertire le due dichiarazioni sposterebbe il problema sul ramo *else* di `isOdd`.

I prototipi di funzione risolvono esattamente questo problema con un overhead costante per controllare che la dichiarazione di una funzione corrisponda al suo eventuale prototipo. In questo modo è possibile ovviare al problema ed il seguente codice è completamente legale ed eseguibile in *Linf*.

```Javascript
isEven(int x, var bool out);
isOdd(int x, var bool out);

isEven(int x, var bool out) {
    if (x == 0) then {
        out = true;
    } else {
        isOdd(x - 1, out);
    }
}

isOdd(int x, var bool out) {
    if (x == 0) then {
        out = false;
    } else {
        isEven(x - 1, out);
    }
}
```

# Compilatore Linf

Il compilatore si avvale del visitor `LinfVisitorImpl.java` per scorrere l'albero di parsing e costruire l'albero di sintassi astratta formato dai nodi di vario tipo.

L'analisi statica effettuata dal compilatore si compone di tre fasi:

1. **Analisi lessicale**: il compilatore controlla la sintassi del programma, dividendo il codice sorgente in token
2. **Analisi semantica**: viene controllato che le varie parti che compongono il programma siano consistenti fra di loro, in base al loro significato (es. type checking)
3. **Controllo dei tipi**:  viene controllata la corretta correlazione tra i tipi

Si noti che il fallimento di una fase implica l'interruzione dell'esecuzione.
Infine il compilatore visita l'albero una quarta volta generando il bytecode di ogni nodo.

## Analisi lessicale

In questa fase viene preso in input il codice sorgente del programma e viene restituita una lista di tutti i token identificati.
In caso di errore, viene restituita la riga di codice e la posizione del token che ha causato l'errore.

## Analisi semantica

In questa fase si visita l'albero di sintassi astratta costruito dal parser e si cercano eventuali errori semantici.

Per ogni nuovo identificatore che viene dichiarato, si aggiunge una entry corrispondente nell'environment (implementato tramite lista di tabelle hash).

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

In *Linf* dunque il comportamento di un blocco è stato definito come l'unione dei comportamenti degli statement $s$ contenuti in quel blocco. Più formalmente possiamo definire il comportamento di un blocco $B$ come la coppia di insiemi di identificatori

$$RW = \mathop{\bigcup}_{s \in B} RW_s$$

$$DEL = \mathop{\bigcup}_{s \in B} DEL_s$$

Gli insiemi $RW$ e $DEL$ contengono rispettivamente tutti gli identificatori acceduti **non locali** in lettura/scrittura e tutti gli identificatori **non locali** cancellati (da un comando `delete` o da una chiamata di funzione) all'interno di un blocco.

Ogni blocco al termine del proprio controllo di tipo confronta propri insiemi $RW$ e $DEL$ per verificare la regola 1 e nel caso incotri un conflitto lancia l'errore `IncompatibleBehaviourError`.

Per quanto riguarda l'*if-then-else*, in fase di controllo di tipo il compilatore verifica che gli insiemi $DEL$ del ramo *then* e del ramo *else* siano uguali.

Il comportamento delle funzioni è simile. Quando una funzione viene dichiarata si inseriscono nell'environment, oltre al tipo della funzione, anche i suoi insiemi $RW$ e $DEL$. Successivamente, quando la funzione viene invocata, si controlla che gli identificatori cancellati dalla funzione (compresi i parametri passati per riferimento) non siano già presenti nell'insieme $DEL$ del blocco chiamante e nel caso viene sollevato l'errore `DoubleDeletionError`. 

Inoltre, non è possibile cancellare l'identificatore di una funzione nel corpo stesso della funzione.

### Errori di tipo

Gli errori di tipo che possono essere catturati sono:

- `DoubleDeletion`: un id viene cancellato due o più volte
- `IncompatibleBehaviour`: un identificatore a cui si accede in lettura e/o scrittura, è stata cancellato
- `IncompatibleTypes`: il tipo di lhs e rhs non coincidono
- `MismatchedPrototype`: il tipo del prototipo della funzione non corrisponde con la dichiarazione
- `ReferenceParameter`: il parametro attuale è un'espressione invece che l'identificatore di una variabile, mentre il parametro formale è passato per riferimento
- `UnbalancedDeletionBehaviour`: i branch dell'if-then-else non cancellano gli stessi identificatori
- `WrongParameterType`: il tipo del parametro formale della funzione non corrisponde con quello del parametro attuale

## Generazione di codice

Anche la generazione di codice beneficia in maniera non indifferente dell'assenza di sottotipaggio nel linguaggio *Linf*: tutti i tipi di dato primitivi hanno la stessa dimensione (4 byte) che è pari alla word della LVM.

La generazione di codice del compilatore *Linf* rispetta l'invariante che l'esecuzione del codice generato debba lasciare invariato il valore dello *stack pointer* e del *frame pointer*.

Ogni blocco riserva le prime tre locazioni di memoria per:

1. **Control link**: detto anche *dynamic link*, ovvero il valore del *frame pointer* nell'ambiente chiamante
2. **Access link**: detto anche *static link*, ovvero il valore del *frame pointer* nell'ambiente in cui è stato definito il blocco
3. **Indirizzo di ritorno**: l'indirizzo dell'istruzione successiva alla chiamata della funzione

dunque il *frame pointer* all'interno di un blocco punta sempre alla prima locazione di memoria dopo l'indirizzo di ritorno.

**N.B.** nel caso di blocchi annidati o di funzioni chiamate nello stesso blocco in cui sono dichiarate, il control link e l'access link possono coincidere.

### Identificatori di variabile

Se l'identificatore è stato definito nel blocco in cui viene utilizzato la generazione di codice si riduce ad una istruzione che accede ad un determinato offset dal valore del frame pointer.

Quando una chiamata di funzione, mentre genera codice per i suoi argomenti, deve generare il codice di un parametro passato per riferimento, invece che pusharne il valore, **risale all'indirizzo** della locazione di memoria puntata dal parametro attuale (che può essere a sua volta un parametro per riferimento di un'eventuale funzione chiamante) e lo pusha al posto del valore dell'identificatore.

Nel caso di un accesso ad identificatori **non locali** si presentano due possibilità: l'identificatore globale nell'ambiente in cui è definito è un parametro passato per riferimento e quindi rappresenta una cella di memoria che può essere acceduta solo risalendo il control link fino a giungere al blocco della chiamata che contiene tra i parametri l'indirizzo della locazione riferita.

Oppure l'identificatore è una variabile libera e quindi va cercata a partire dall'ambiente in cui è stato definito il blocco seguendo prima l'access link ed eventualmente il control link se l'identificatore non è stato dichiarato nello stesso ambiente in cui è definito il blocco.


# Linf Virtual Machine

La macchina virtuale **LVM** (Linf Virtual Machine) che esegue il bytecode è contenuta nel file `LVM.java`.

Anche in questo caso ci si avvale del visitor `LVMVisitorImpl.java`, per visitare il bytecode, mantenendo un array `code` di interi in cui vengono inseriti i codici delle istruzioni macchina man mano che vengono lette.
Successivamente l'array `code` viene passato alla **LVM** che lo esegue, istruzione per istruzione.

# Conclusioni

È possibile dimostrare la Turing-Completezza del linguaggio *Linf* dimostrando che è in grado di calcolare tutte e sole le funzioni **generali ricorsive** dette anche $\mu$-ricorsive. Le funzioni generali ricorsive sono funzioni parziali ricorsive che prendono tuple finite di numeri naturali e ritornano un singolo numero naturale. Sono la più piccola classe di funzioni parziali che include le funzioni iniziali ed è chiusa per composizione, ricorsione primitiva e minimizzazione non limitata.

## Funzioni Costanti

```Javascript
const(var int out) {
    out = 0;
}
```

## Funzione Successore

```Javascript
s(int n, var int out){
    out = n + 1;
}
```

## Funzione Proiezione

```Javascript
proj1(int x1, int x2, int x3, var int out) {
    out = x1;
}

proj2(int x1, int x2, int x3, var int out) {
    out = x2;
}
```

## Composizione

```Javascript
h(int a, int b, int c, int d, var int out){
    out = a + b + c + d;
    print out;
}

g1(int x, var int out){
    out = x * x;
}

g2(int x, var int out){
    out = x + x;
}

g3(int x, var int out){
    out = x * 2 * x;
}

g4(int x, var int out){
    out = x / 2 * x;
}

compose(int a, var int out){
    int a1 = -1;
    int b1 = -1;
    int c1 = -1;
    int d1 = -1;

    g1(a, a1);
    g2(a, b1);
    g3(a, c1);
    g4(a, d1);

    h(a1, b1, c1, d1, out);
}
```

## Ricorsione primitiva

```Javascript
f(int a, int b, var int out) {
    out = a + b;
}

p(int a, int b, int c, int d, var int out){
     out = a + b - c + d;
}

rho(
    int x0,
    int x1,
    int x2,
    var int out)
{
   f(x1, x2, out);

   innerRho(int x0, int x1, int x2, int i, var int out) {
        if (i < x0) then {
            p(i,out,x1,x2,out);
            innerRho(x0, x1, x2, i + 1, out);
         } else {}
    }

   innerRho(x0,x1,x2,0,out);
}
```

## Minimizzazione non limitata

```Javascript
fun(int a, int b, int c, var int out) {
    out = a - b + c;
}

mu(
    int x1,
    int x2,
    var int out)
{
    innerMu(int i, int x1, int x2, var int out) {
        fun(i,x1,x2, out);
        if (out == 0) then {
            out = i;
        } else {
            innerMu(i + 1, x1, x2, out);
        }
    }

    innerMu(0, x1, x2, out);
}
```

# Test

## Fattoriale

```Javascript
int ONE = 1;
printfactorial(int x) {
    fact(var int out, int x) {
          if ( x > 1 ) then {
              fact(out, x - 1);
              out = x * out;
          } else {
              out = ONE;
          }
    }

   int out = 1;
   {{ fact(out, x); }}

    print out;
}

printfactorial(10);
```

Stampa 3628800.

## Fibonacci

```Javascript
fibonacci(int x, var int out) {
    if (x <= 1) then {
       out = x;
    } else {
        fibonacci(x - 1, out);
        int old = out;
        fibonacci(x - 2, out);
        out = out + old;
    }
}
int out = 0;

fibonacci(0, out);
print out;

fibonacci(1, out);
print out;

fibonacci(7, out);
print out;
```

Stampa 0, 1, 13.


## IsEven/IsOdd

```Javascript
bool out = false;

isEven(int x, var bool out);
isOdd(int x, var bool out);

isEven(int x, var bool out) {
    if (x == 0) then {
        out = true;
    } else {
        isOdd(x - 1, out);
    }
}

isOdd(int x, var bool out) {
    if (x == 0) then {
        out = false;
    } else {
        isEven(x - 1, out);
    }
}

isEven(10, out);
print out;

isOdd(10, out);
print out;
```

Stampa 1 e 0.


## Funzione di Ackermann

```Javascript
int out = -1;

ack(int m, int n, var int out) {
    if (m == 0) then {
        out = n + 1;
    } else {}

    if ((m > 0) && (n == 0)) then {
        ack(m - 1, 1, out);
    } else {}

    if ((m > 0) && (n > 0)) then {
        ack(m, n - 1, out);
        ack(m - 1, out, out);
    } else {}
}

ack(1, 2, out);
print out;
```

Stampa 4.

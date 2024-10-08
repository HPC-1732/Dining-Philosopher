## This repository simulates the following multithreaded program:

In the classical Dining Philosophers problem, 5 philosophers are
seated   around   a   round   table   with   a   fork   between   each   pair.   A
philosopher may only eat when he/she is holding both forks next to
him/her (they’re really hungry so use 2 forks to eat instead of 1). A
philosopher   will   alternately   think,   try   to   pick   up   the   forks   next   to
him/her,   and   if   successful,   start   eating,   after   which   he/she   starts
thinking again. You may suppose there is a limitless supply of food and
the   philosophers’   appetite   is   also   limitless.   Now   assume   that   the
philosophers don’t speak to each other (they are too busy thinking and
eating), and that each philosopher follows the protocol below:
1. Think for a random amount of time (between 0 and 10 seconds).
2. Become hungry, so try to pick up left fork.
3. If   successful,   wait   4   seconds,   then   try   to   pick   up   right   fork.
Otherwise, keep waiting until the left fork becomes available.
4. If successful in picking up the right fork, begin eating. Otherwise,
keep waitinguntil the right fork becomes available.
5. Eat for a random amount of time (between 0 and 5 seconds).
6. Put down his/her left fork.
7. Put down his/her right fork, and return to step 1.
It   can   be   shown   that   the   above   protocol   will   eventually   enter   a
deadlocked state if run long enough.
For   this   assignment,   you   are   asked   to   consider   a   scenario   where
instead of just one table with 5 philosophers, there are 5 tables, each
with 5 philosophers, and a sixth table where there are no philosophers
sitting, but there are also 5 forks placed in the same arrangement as
the other tables.
If deadlock occurs at any table, one of the philosophers drops any fork
he/she is holding, and moves to a random unoccupied seat at the sixth
table. Eventually, the sixth table will also have 5 philosophers seated at
it, and therefore, it too will eventually enter a deadlocked state.
In this assignment, you are asked to simulate the above protocol using
a thread for each philosopher and determine how long it takes for the
sixth   table   to   enter   a   state   of   deadlock.   Also,   assuming   the
philosophers are initially labelled with the first 25 uppercase letters of
the   English   alphabet,   you   are   asked   to   output   the   label   of   the
philosopher   who   last   moved   to   the   sixth   table   before   it   became
deadlocked.
You may use a simulated clock as instructed in Assignment 2, or a real-
time clock for each thread. If you use a real-time clock you may scale all
the times above by a constant factor in order to make your simulation
run faster.

## How to Run

Clone the repository.

```bash
git clone https://github.com/HPC-1732/Dining-Philosopher.git
```

Compile the files.
```bash
javac DiningPhilosophers.java;
```

Now run the program.
```bash
java DiningPhilosophers.java;
```
"# Universe_Simulation" 

Reads the universe with N bodies from StdIn
Simulates it according to your chocice of algorithm
- if brute in O(N^2)
- if quad in O(NlogN)

Input files: ./inputs/*.txt 

Compilation: javac NBody.java

For simulating:
java NBody 100 0.1 quad < inputs/input2.txt > output.txt
java NBody 250000000 25000 brute < inputs/planets.txt > output.txt


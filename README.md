# How To

To run benchmark, call jhm:run from the sbt console in the project directory (root dir of this repo)

```
sbt
jmh:run -wi 5 -i 5 -f1 -t1
```

# Results

On my machine (OSX 10.11.6, 2,7 GHz Intel Core i7, JDK 1.8.0_152):


```
[info] Benchmark                 (fileName)  Mode  Cnt     Score     Error  Units
[info] JacksonTest.read         cookies.csv  avgt    5   859,985 ± 113,025  ms/op
[info] JacksonTest.read  worldcitiespop.txt  avgt    5  1815,327 ± 180,060  ms/op
[info] SfmTest.read             cookies.csv  avgt    5   951,117 ±  28,624  ms/op
[info] SfmTest.read      worldcitiespop.txt  avgt    5  1421,647 ±  49,554  ms/op
```

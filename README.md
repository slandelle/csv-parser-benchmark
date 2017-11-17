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
[info] JacksonTest.read         cookies.csv  avgt    5   843,680 ± 242,878  ms/op
[info] JacksonTest.read  worldcitiespop.txt  avgt    5  3263,942 ± 580,845  ms/op
[info] SfmTest.read             cookies.csv  avgt    5   959,731 ±  32,169  ms/op
[info] SfmTest.read      worldcitiespop.txt  avgt    5  2845,387 ± 162,184  ms/op
```

On Linux:

```
[info] Benchmark                 (fileName)  Mode  Cnt     Score    Error  Units
[info] JacksonTest.read         cookies.csv  avgt    5   583.568 ± 22.140  ms/op
[info] JacksonTest.read  worldcitiespop.txt  avgt    5  2474.952 ± 64.665  ms/op
[info] SfmTest.read             cookies.csv  avgt    5   656.975 ± 31.024  ms/op
[info] SfmTest.read      worldcitiespop.txt  avgt    5  2183.237 ± 51.007  ms/op
```

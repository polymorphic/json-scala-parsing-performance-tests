# JSON Scala parsing performance tests — Performance tests for Scala JSON parsing options #

## Build & run ##

```sh
$ cd JSON-Scala-parsing-performance-tests
$ ./sbt
> run
```

There are three command-line arguments:

com.microWorkflow.jsonScalaPerftest.Main [-n num] [-w num] [-map]

-n is the number of iterations to run; the default value is 100, and the result is the mean value
-w is the number of iterations for warm-up, i.e., executed prior to taking measurements; the default
value is 5
-map is optional and determines whether the measurements cover the
object model mapping in addition to JSON parsing. Without this argument the measurements
cover only the latter


## Contact ##

- Dragos Manolescu
- <a href="coder@micro-workflow.com">coder@micro-workflow.com</a>

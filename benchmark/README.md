# Benchmark results

JMH version: 1.21
VM version: JDK 1.8.0_171, OpenJDK 64-Bit Server VM, 25.171-b11
Warmup: 5 iterations, 10 s each
Measurement: 15 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 1 thread, will synchronize iterations
Benchmark mode: Throughput, ops/time

Benchmark | Mode | Cnt | Score | Error | Units
--------- | ---- | --- | ----- | ----- | -----
JaBASerializer Album | thrpt | 75 | 123303,156 | ± 3616,227 | ops/s
Kryo Album | thrpt | 75 | 42632,562 | ± 1559,859 |  ops/s
Protobuf Album | thrpt | 75 | 691276,430 | ± 19205,396 |  ops/s

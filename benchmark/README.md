# Benchmark results

JMH version: 1.21\
VM version: JDK 1.8.0_171, OpenJDK 64-Bit Server VM, 25.171-b11\
Warmup: 5 iterations, 10 s each\
Measurement: 15 iterations, 10 s each\
Timeout: 10 min per iteration\
Threads: 1 thread, will synchronize iterations\
Benchmark mode: Throughput, ops/time

## Serialization of [Album](src/main/java/com/github/mismatch/serializer/dto/Album.java) Album object (standard types)

Benchmark | Mode | Cnt | Score | Error | Units
--------- | ---- | --- | ----- | ----- | -----
JaBASerializer | thrpt | 75 | 123303,156 | ± 3616,227 | ops/s
Kryo | thrpt | 75 | 42632,562 | ± 1559,859 |  ops/s
Protobuf | thrpt | 75 | 691276,430 | ± 19205,396 |  ops/s

## Serialization of [Parcel](src/main/java/com/github/mismatch/serializer/dto/Parcel.java) Parcel object (custom types)

Benchmark | Mode | Cnt | Score | Error | Units
--------- | ---- | --- | ----- | ----- | -----
JaBASerializer | thrpt | 75 | 142984,578 | ± 5198,242 | ops/s
Kryo | thrpt | 75 | 39980,080 | ± 1134,114 | ops/s
Protobuf | thrpt | 75 | 934098,658 | ± 29458,935 | ops/s

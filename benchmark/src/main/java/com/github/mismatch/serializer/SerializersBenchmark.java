package com.github.mismatch.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.github.mismatch.serializer.converters.Converters;
import com.github.mismatch.serializer.dto.Album;
import com.github.mismatch.serializer.dto.AlbumProtos;
import com.github.mismatch.serializer.units.UnitFactory;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SerializersBenchmark {

	@State(Scope.Benchmark)
	public static class ExecutionPlan {
		public Serializer serializer;

		public Album simpleObject;

		public Kryo kryo;
		public Output kryoOutput;

		@Setup(Level.Invocation)
		public void setup() {
			Converters converters = new Converters();
			converters.addDefault();
			serializer = new Serializer(new UnitFactory(converters));

			kryo = new Kryo();
			kryoOutput = new Output();

			simpleObject = new Album("Songs from the Big Chair",
					Collections.singletonList("Tears For Fears"), 1985,
					Arrays.asList("Shout", "The Working Hour", "Everybody Wants to Rule the World",
							"Mothers Talk", "I Believe", "Broken", "Head Over Heels", "Listen"));
		}

		@TearDown(Level.Invocation)
		public void tearDown() {
			kryoOutput.close();
		}
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testJaBASerializesSimpleObject(ExecutionPlan plan) {
		final byte[] binaryAlbum = plan.serializer.serialize(plan.simpleObject);
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testProtobufSerializesSimpleObject(ExecutionPlan plan) {
		AlbumProtos.Album albumMessage = AlbumProtos.Album.newBuilder()
				.setTitle(plan.simpleObject.getTitle())
				.addAllArtist(plan.simpleObject.getArtists())
				.setReleaseYear(plan.simpleObject.getReleaseYear())
				.addAllSongTitle(plan.simpleObject.getSongsTitles())
				.build();

		final byte[] binaryAlbum = albumMessage.toByteArray();
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testKryoSerializesSimpleObject(ExecutionPlan plan) {
		plan.kryoOutput.setBuffer(new byte[256]);
		plan.kryo.writeObject(plan.kryoOutput, plan.simpleObject);

		final byte[] binaryAlbum = plan.kryoOutput.toBytes();
	}
}

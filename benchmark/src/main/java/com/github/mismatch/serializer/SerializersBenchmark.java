package com.github.mismatch.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.github.mismatch.serializer.converters.Converters;
import com.github.mismatch.serializer.dto.*;
import com.github.mismatch.serializer.units.UnitFactory;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SerializersBenchmark {

	@State(Scope.Benchmark)
	public static class ExecutionPlan {
		public Serializer serializer;

		public Album album;

		public Kryo kryo;
		public Output kryoOutput;
		public Parcel parcel;

		@Setup(Level.Invocation)
		public void setup() {
			Converters converters = new Converters();
			converters.addDefault();
			serializer = new Serializer(new UnitFactory(converters));

			kryo = new Kryo();
			kryoOutput = new Output();

			album = new Album("Songs from the Big Chair",
					Collections.singletonList("Tears For Fears"), 1985,
					Arrays.asList("Shout", "The Working Hour", "Everybody Wants to Rule the World",
							"Mothers Talk", "I Believe", "Broken", "Head Over Heels", "Listen"));

			parcel = new Parcel(15228, "Test parcel", new Address("USA", "Nashville", "TN 37210"),
					new Volume(120.0, 66.3, 75.27, DimensionUnit.IN), new Weight(41465.45, WeightUnit.LB));
		}

		@TearDown(Level.Invocation)
		public void tearDown() {
			kryoOutput.close();
		}
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testJaBASerializesAlbum(ExecutionPlan plan) {
		final byte[] binaryAlbum = plan.serializer.serialize(plan.album);
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testProtobufSerializesAlbum(ExecutionPlan plan) {
		AlbumProtos.Album albumMessage = AlbumProtos.Album.newBuilder()
				.setTitle(plan.album.getTitle())
				.addAllArtist(plan.album.getArtists())
				.setReleaseYear(plan.album.getReleaseYear())
				.addAllSongTitle(plan.album.getSongsTitles())
				.build();

		final byte[] binaryAlbum = albumMessage.toByteArray();
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testKryoSerializesAlbum(ExecutionPlan plan) {
		plan.kryoOutput.setBuffer(new byte[256]);
		plan.kryo.writeObject(plan.kryoOutput, plan.album);

		final byte[] binaryAlbum = plan.kryoOutput.toBytes();
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testJaBASerializesParcel(ExecutionPlan plan) {
		final byte[] binaryParcel = plan.serializer.serialize(plan.parcel);
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testProtobufSerializesParcel(ExecutionPlan plan) {
		ParcelProtos.Parcel parcelMessage = ParcelProtos.Parcel.newBuilder()
				.setId(plan.parcel.getId())
				.setDescription(plan.parcel.getDescription())
				.setDestinationAddress(ParcelProtos.Parcel.Address.newBuilder()
						.setCountry(plan.parcel.getDestinationAddress().getCountry())
						.setTown(plan.parcel.getDestinationAddress().getTown())
						.setPostcode(plan.parcel.getDestinationAddress().getPostcode())
						.build())
				.setVolume(ParcelProtos.Parcel.Volume.newBuilder()
						.setLength(plan.parcel.getVolume().getLength())
						.setWidth(plan.parcel.getVolume().getWidth())
						.setHeight(plan.parcel.getVolume().getHeight())
						.setUnit(ParcelProtos.Parcel.DimensionUnit.forNumber(plan.parcel.getVolume().getUnit().ordinal()))
						.build())
				.setGrossWeight(ParcelProtos.Parcel.Weight.newBuilder()
						.setValue(plan.parcel.getGrossWeight().getValue())
						.setUnit(ParcelProtos.Parcel.WeightUnit.forNumber(plan.parcel.getGrossWeight().getUnit().ordinal()))
						.build())
				.build();

		final byte[] binaryAlbum = parcelMessage.toByteArray();
	}

	@Benchmark
	@Measurement(iterations = 15, time = 10, timeUnit = TimeUnit.SECONDS)
	public void testKryoSerializesParcel(ExecutionPlan plan) {
		plan.kryoOutput.setBuffer(new byte[256]);
		plan.kryo.writeObject(plan.kryoOutput, plan.parcel);

		final byte[] binaryParcel = plan.kryoOutput.toBytes();
	}
}

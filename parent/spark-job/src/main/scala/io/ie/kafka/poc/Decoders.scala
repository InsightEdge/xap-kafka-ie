package io.ie.kafka.poc

import java.io.{ByteArrayInputStream, IOException, ObjectInputStream}

import kafka.serializer.Decoder
import kafka.utils.VerifiableProperties
import org.common.PriceFeed


class PriceFeedStreamDecoder(props: VerifiableProperties = null) extends Decoder[PriceFeed] {
  override def fromBytes(bytes: Array[Byte]): PriceFeed = try {
    // TODO taken from commons-lang3, use Kryo
    println("Stream decoding: " + bytes)
    if (bytes == null) throw new IllegalArgumentException("bytes should not be null")
    val in = new ObjectInputStream(new ByteArrayInputStream(bytes))
    try {
      @SuppressWarnings(Array("unchecked")) val obj = in.readObject.asInstanceOf[PriceFeed]
      println("Stream decoded: " + obj)
      obj
    } catch {
      case ex@(_: ClassNotFoundException | _: IOException) =>
        throw new RuntimeException(ex)
    } finally if (in != null) in.close()
  }
}


/**
  * @author Danylo_Hurin.
  */
//class PriceFeedDecoder(props: VerifiableProperties = null) extends Decoder[PriceFeed] {
//  override def fromBytes(bytes: Array[Byte]): PriceFeed = {
//    println("Decoding: " + bytes)
//    val p = new PriceFeed()
//    p.setId("id")
//    p.setPrice(1.0f)
//    p.setSymbol("A")
//    println(p)
//    println("Classloader of PriceFeed: " + classOf[PriceFeed].getClassLoader)
//    println("Classloader of SerializationUtils: " + classOf[SerializationUtils].getClassLoader)
//    val v: PriceFeed = SerializationUtils.deserialize[PriceFeed](bytes)
//    println("Decoded: " + v)
//    v
//  }
//}


package jmh.main

import scala.collection.immutable.{AbstractMap, Map}

object ArrayBasedMap {
  def apply[K, V](keys: Array[K], values: Array[V]): ArrayBasedMap[K, V] =
    new ArrayBasedMap(keys, values, math.min(keys.length, values.length))
}

class ArrayBasedMap[K, +V](keys: Array[K], values: Array[V], override val size: Int) extends AbstractMap[K, V] with Map[K, V] with Serializable {

  override def +[V1 >: V](kv: (K, V1)) =  throw new UnsupportedOperationException

  override def updated [V1 >: V] (key: K, value: V1): Map[K, V1] =  throw new UnsupportedOperationException

  override def get(key: K): Option[V] = {
    var i = 0
    while (i < size) {
      if (keys(i) == key) {
        return Some(values(i))
      }
      i += 1
    }
    None
  }

  override def iterator: Iterator[(K, V)] = new Iterator[(K, V)] {

    private var i = 0

    override def hasNext = i < ArrayBasedMap.this.size

    override def next() = {
      val v = (keys(i), values(i))
      i += 1
      v
    }
  }

  override def -(key: K) = throw new UnsupportedOperationException
}

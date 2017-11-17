package jmh.main

import java.io.{File, FileInputStream, InputStreamReader}
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import java.util.{Iterator => JIterator}

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@State(Scope.Thread)
abstract class CsvText {

  @Param(Array("cookies.csv", "worldcitiespop.txt"))
  var fileName: String = _

  protected def csvFile: File = new File("/Users/slandelle/Documents/dev/csv-samples", fileName)

  protected def readValues(reader: InputStreamReader): JIterator[Array[String]]

  @Benchmark
  def read(bh: Blackhole): Unit = {
    val reader = new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8)
    try {
      val it = readValues(reader)
      val headers = it.next()
      while (it.hasNext) {
        val values = it.next()
        bh.consume(Map.empty ++ ArrayBasedMap(headers, values))
      }
    } finally {
      reader.close()
    }
  }
}

object JacksonTest {

  import com.fasterxml.jackson.databind.MapperFeature
  import com.fasterxml.jackson.dataformat.csv.{CsvSchema, CsvMapper => JacksonMapper, CsvParser => JacksonCsvParser}

  private val objectReader = {
    val mapper = new JacksonMapper()
      .enable(JacksonCsvParser.Feature.WRAP_AS_ARRAY)
      .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
    val schema = CsvSchema.emptySchema.withColumnSeparator(',').withQuoteChar('"')
    mapper.readerFor(classOf[Array[String]]).`with`(schema)
  }
}

class JacksonTest extends CsvText {

  import JacksonTest._

  override protected def readValues(reader: InputStreamReader): JIterator[Array[String]] =
    objectReader.readValues(reader)
}

object SfmTest {

  import org.simpleflatmapper.csv.CsvParser

  private val dsl = CsvParser.separator(',').quote('"')//.disableUnescaping
}

class SfmTest extends CsvText {

  import SfmTest._

  override protected def readValues(reader: InputStreamReader): JIterator[Array[String]] =
    dsl.iterator(reader)
}

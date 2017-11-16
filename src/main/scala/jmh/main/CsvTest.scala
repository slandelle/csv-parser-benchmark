package jmh.main

import java.io.{File, FileReader}
import java.util.{Map => JMap}
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@State(Scope.Thread)
abstract class CsvText {

  @Param(Array("cookies.csv", "worldcitiespop.txt"))
  var fileName: String = _

  protected def csvFile: File = new File("/Users/slandelle/Documents/dev/csv-samples", fileName)
}

object JacksonTest {
  import com.fasterxml.jackson.databind.MapperFeature
  import com.fasterxml.jackson.dataformat.csv.{CsvSchema, CsvMapper => JacksonMapper}

  val Mapper = new JacksonMapper().disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
  val Schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',').withQuoteChar('"')
}

class JacksonTest extends CsvText {
  import JacksonTest._

  @Benchmark
  def read(bh: Blackhole): Unit = {
    val objectReader = Mapper.readerFor(classOf[JMap[_, _]]).`with`(Schema)
    val reader = new FileReader(csvFile)
    try {
      val it = objectReader.readValues(reader)
      while (it.hasNext) {
        bh.consume(it.next().asInstanceOf[Object])
      }
    } finally {
      reader.close()
    }
  }
}

object SfmTest {
  import org.simpleflatmapper.csv.CsvParser
  private val MapToDSL = CsvParser.separator(',').quote('"').disableUnescaping.mapTo(classOf[JMap[_, _]])
}

class SfmTest extends CsvText {

  import SfmTest._

  @Benchmark
  def read(bh: Blackhole): Unit = {
    val reader = new FileReader(csvFile)
    try {
      val it = MapToDSL.iterator(reader)
      while (it.hasNext) {
        bh.consume(it.next().asInstanceOf[Object])
      }
    } finally {
      reader.close()
    }
  }
}

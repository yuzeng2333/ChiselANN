
package deeplearning

import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

object OutputLayerSuit extends App{

  def runOutputTester(
    ifname:String,
    rfname:String,
    dtype:SInt,
    inNo:Int,
  ) : Unit = {
    Driver(() => new OutputLayer(dtype,inNo)){
      o => new OutputLayerTester(o,ifname,rfname,dtype)
    }
  }

  class OutputLayerTester(
    c : OutputLayer,
    ifname:String,
    rfname:String,
    dtype:SInt
  )extends PeekPokeTester(c){
    val inputs: Seq[SInt] = TestTools.getOneDimArryAsSInt(ifname,dtype)
    for(i <- inputs.indices){
      poke(c.io.input(i),inputs(i))
    }
    step(c.latency)
    expect(c.io.output,7.U)
  }

  def testOutputLayer():Unit = {
    val input_file_name = "dense1_output_7.csv"
    runOutputTester(input_file_name,"",SInt(16.W),10)
  }
  testOutputLayer()
}

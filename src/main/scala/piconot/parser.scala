package piconot

import scala.util.parsing.combinator._
import picolib.semantics._
import scala.collection.mutable.Map

object BotParser extends JavaTokenParsers with PackratParsers {
  
  // parsing interface
  def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)
  
  def program: Parser[List[Rule]] = rule * 

  def rule: Parser[Rule] = 
    "From " ~ state ~ ", " ~ // current state
    "if [" ~ cond ~ "] -> " ~ // status of surrounding walls
    "go " ~ dir ~ ", " ~ // what direction to proceed in
    "change to " ~ state ^^ { // what state to change to afterwards
      case "From " ~ f ~ ", " ~
      "if [" ~ i ~ "] -> " ~ 
      "go " ~ g ~ ", " ~
      "change to " ~ c => Rule(f, i, g, c)
    }

  def state: Parser[State] = 
    "State " ~ wholeNumber ^^ {case "State " ~ n => State(n)}

  // Takes a string in the format "dir: wallStatus, dir: WallStatus, ..."
  // Example: "N: blocked, S: open, E: blocked"
  def cond: Parser[Surroundings] = 
    dirList ^^ {case d => Surroundings(
      north = d("N"), east = d("E"), west = d("W"), south = d("S"))} // where d is a map
    
  
  def dirList: Parser[Map[String, RelativeDescription]] = 
    repsep(dirCond, ",") ^^ {case d => // create a map where the key value pair is (dir, wallStatus)
                                var dMap: Map[String, RelativeDescription] = Map(
                                  ("N", Anything), ("E", Anything), // by default, wallStatus can be anything
                                  ("W", Anything), ("S", Anything)) 
                                val newMap = d.toMap // d is a list of (String, RelativeDescription) tuples
                                dMap ++= collection.mutable.Map(newMap.toSeq: _*) // update wallStatus if necessary
                                dMap
                            }
  
  // Takes a string in the format "dir: wallStatus"
  // Example: "N: blocked"
  def dirCond: Parser[(String, RelativeDescription)] = 
    "(N|E|W|S)".r ~ ": " ~ wallStatus ^^ {case d ~ ": " ~ ws => (d, ws)}
  
  def wallStatus: Parser[RelativeDescription] = 
      "blocked" ^^ {case s => Blocked }
    | "open" ^^ {case s => Open }

  def dir: Parser[MoveDirection] = 
      "Nowhere" ^^ {case n => StayHere}
    | "N" ^^ {case n => North}
    | "E" ^^ {case n => East}
    | "W" ^^ {case n => West }
    | "S" ^^ {case n => South}
} 


import scala.util.parsing.combinator._
import scala.collection.mutable.Map

object botParser extends JavaTokenParsers with PackratParsers {

  // parsing interface
  def apply(s: String): ParseResult[Rule] = parseAll(rule, s)
  
  lazy val rule: PackratParser[rule] = 
    "From " ~ rule ~ ", " ~
    "if " ~ rule ~ " -> " ~ 
    "go " ~ rule ~ ", " ~
    "change to " ~ rule ^^ { 
      "From " ~ case f ~ ", " ~
      "if " ~ case i ~ " -> " ~ 
      "go " ~ case g ~ ", " ~
      "change to " ~ case c => Rule(f, i, g, c)
    }
    | "State " ~ wholeNumber ^^ { case n => State(n)}
    | "[" ~ list ~ "]" ^^ {"[" ~ case l ~ "]" => parseList(l)} 
    | "N" => North 
    | "E" => East 
    | "W" => West 
    | "S" => South
}

/* Given a string in the format "dir: position, dir: position...", 
return a corresponding tuple with RelativeDescription correlating to
(North, East, West, South)
*/
def parseList(str: String): (RelativeDescription)  = {
  // Initialize a map where all directions have no condition by default
  var cond = collection.mutable.Map("N" -> Anything,
                                    "E" -> Anything,
                                    "W" -> Anything,
                                    "S" -> Anything)
  // Split string by direction such that ["dir: position", "dir:position,"...]
  val arr = str.split(",")
  // Update map
  for (el <- arr) {
    var res = el.split(": ") // Split string such that ["dir", "position"]
    cond(res(0)) = strToRelDesc(res(1)) 
  }
  // Return tuple with RelativeDescription values
  (cond('N'), cond('E'), cond('W'), cond('S'))
}

/* Given a string, return corresponding RelativeDescription
*/
def strToRelDesc(str: String): RelativeDescription = str match 
  case "blocked" => Blocked 
  case "open" => Open 
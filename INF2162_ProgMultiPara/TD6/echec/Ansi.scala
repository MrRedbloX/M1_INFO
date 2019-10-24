object Ansi {
  final var black = code("black")
  final var red = code("red")
  final var green = code("green")
  final var yellow = code("yellow")
  final var blue = code("blue")
  final var magenta = code("magenta")
  final var cyan = code("cyan")
  final var white = code("white")
  final var fblack = code("fblack")
  final var fred = code("fred")
  final var fgreen = code("fgreen")
  final var fyellow = code("fyellow")
  final var fblue = code("fblue")
  final var fmagenta = code("fmagenta")
  final var fcyan = code("fcyan")
  final var fwhite = code("fwhite")
  def code(code_ : String) : String={
    var ret : String = "\u001B"
    code_ match{
      case "black" => ret = ret + "[30m"
      case "red" => ret = ret + "[31m"
      case "green" => ret = ret + "[32m"
      case "yellow" => ret = ret + "[33m"
      case "blue" => ret = ret + "[34m"
      case "magenta" => ret = ret + "[35m"
      case "cyan" => ret = ret + "[36m"
      case "white" => ret = ret + "[37m"
      case "fblack" => ret = ret + "[40m"
      case "fred" => ret = ret + "[41m"
      case "fgreen" => ret = ret + "[42m"
      case "fyellow" => ret = ret + "[43m"
      case "fblue" => ret = ret + "[44m"
      case "fmagenta" => ret = ret + "[45m"
      case "fcyan" => ret = ret + "[46m"
      case "fwhite" => ret = ret + "[47m"
    }
    ret
  }
  def reset():String = "\u001B[0m";
}

object Ansi{
  def code(code_ : String) : String={
    var ret : String = "\u001B"
    code_ match{
      case "fBlack" => ret += "[30m"
      case "fRed" => ret += "[31m"
      case "fGreen" => ret += "[32m"
      case "fYellow" => ret += "[33m"
      case "fBlue" => ret += "[34m"
      case "fMagenta" => ret += "[35m"
      case "fCyan" => ret += "[36m"
      case "fWhite" => ret += "[37m"
      case "black" => ret += "[40m"
      case "red" => ret += "[41m"
      case "green" => ret += "[42m"
      case "yellow" => ret += "[43m"
      case "blue" => ret += "[44m"
      case "magenta" => ret += "[45m"
      case "cyan" => ret += "[46m"
      case "white" => ret += "[47m"
    }
    ret;
  }

  def reset():String = {
    "\u001B[0m";
  }
}

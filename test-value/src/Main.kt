fun main(args: Array<String>) {
    val user=User()

    AnnotationExpression(user).expression()

    println(user.toString())
}
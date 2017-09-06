class User{
    @Value(value = "name")
    lateinit var name:String

    @Value(value = "age")
    var age:Int=0

    @Value(value = "money")
    var money:Double=0.0

    @Value(value = "gender")
    var gender:Boolean=false

    override fun toString(): String {
        return "(name:$name; age:$age; money:$money; gender:${if(gender) "man" else "woman"})"
    }
}
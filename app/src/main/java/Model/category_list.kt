package Model

class category_list {
    private var id: Int = 0
    private var type: String = ""



    constructor(Id: Int, Type: String) {
        this.id = Id
        this.type = Type
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }
}
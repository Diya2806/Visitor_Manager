package Model

class model_id {
    private var id: Int = 1
    private var name: String = ""
    private var email: String = ""
    private var number: Int = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getNumber(): Int {
        return number
    }

    fun setNumber(number: Int) {
        this.number = number
    }
}
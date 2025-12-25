package Model


class custom_list {
    private var name : String = ""
    private var number : Int = 0
    private var vehical : String = ""
    private var TimeIn : String = ""
    private var entryDate: String = ""
    private  var TimeOut : String? = null
    private  var Type:String = ""




    constructor(name: String, number: Int, vehical: String,Type: String, TimeIn: String,TimeOut: String?,    entryDate: String ) {
        this.TimeOut = TimeOut
        this.TimeIn = TimeIn
        this.vehical = vehical
        this.Type =Type
        this.number = number
        this.name = name
        this.entryDate = entryDate


    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }
    fun getMobile(): Int {
        return number
    }

    fun setMobile(number: Int) {
        this.number = number
    }
    fun getVehicleNumber(): String {
        return vehical
    }

    fun setVehicleNumber(vehical: String) {
        this.vehical = vehical
    }

    fun getTimeIn(): String {
        return TimeIn
    }

    fun setTimeIn(TimeIn: String) {
        this.TimeIn = TimeIn
    }

    fun getTimeOut(): String? {
        return TimeOut
    }

    fun setTimeOut(TimeOut: String?) {
        this.TimeOut = TimeOut
    }

    fun getEntryDate(): String {
        return entryDate
    }

    fun setEntryDate(entryDate: String) {
        this.entryDate = entryDate
    }
    fun getType():String{
        return Type
    }
    fun setType(Type:String){
        this.Type = Type
    }

}
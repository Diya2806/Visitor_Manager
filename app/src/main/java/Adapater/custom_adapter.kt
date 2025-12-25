package Adapater

import Model.custom_list
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.diya.visitortracker.R

class custom_adapter (
  context:Context,
    private val resourec:Int,
    private val visitorlist:ArrayList<custom_list>
):ArrayAdapter<custom_list>(context,resourec,visitorlist){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(resourec, parent, false)

        val visitor = visitorlist[position]

        val nameView = view.findViewById<TextView>(R.id.custom_name)
        val numberView = view.findViewById<TextView>(R.id.custom_number)
        val vehicleView = view.findViewById<TextView>(R.id.custom_vehical)
        val Type = view.findViewById<TextView>(R.id.custom_vtype)
        val timeInView = view.findViewById<TextView>(R.id.custom_Time_In)
        val timeOutView = view.findViewById<TextView>(R.id.custom_time_out)
        val dates =view.findViewById<TextView>(R.id.custom_entrydate)
        val btn = view.findViewById<Button>(R.id.custom_btn)


        nameView.text = visitor.getName()
        numberView.text = visitor.getMobile().toString()
        vehicleView.text = visitor.getVehicleNumber()
        Type.text = visitor.getType()
        timeInView.text = visitor.getTimeIn()
        timeOutView.text = visitor.getTimeOut()?: "Pending"
        dates.text = visitor.getEntryDate()

        btn.setOnClickListener(){
            Toast.makeText(context, "printing...", Toast.LENGTH_LONG).show()
        }

        return view
    }
}
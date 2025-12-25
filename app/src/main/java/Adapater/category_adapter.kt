package Adapater

import Model.category_list
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.diya.visitortracker.R

class category_adapter(
    context: Context,
    private val resource: Int,
    private val categoryList: ArrayList<category_list>,
    private val checkedItems: HashSet<Int>
) : ArrayAdapter<category_list>(context, resource, categoryList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(resource, parent, false)

        val category = categoryList[position]

        val idView = view.findViewById<TextView>(R.id.custom_cat_id)
        val typeView = view.findViewById<TextView>(R.id.custom_cat_type)
        val checkBox = view.findViewById<CheckBox>(R.id.custom_check)

        val catId = category.getId()
        val catType = category.getType()

        idView.text = catId.toString()
        typeView.text = catType
        checkBox.isChecked = checkedItems.contains(catId)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedItems.add(catId)
            } else {
                checkedItems.remove(catId)
            }
        }

        return view
    }
}
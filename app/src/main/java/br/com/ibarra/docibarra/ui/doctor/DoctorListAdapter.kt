package br.com.ibarra.docibarra.ui.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.ibarra.docibarra.R
import br.com.ibarra.docibarra.data.model.Doctor
import br.com.ibarra.docibarra.di.GlideManager
import kotlinx.android.synthetic.main.view_doctor_list_item.view.*

class DoctorListAdapter(private val glideManager: GlideManager) : PagedListAdapter<Doctor, DoctorListAdapter.DoctorHolder>(itemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DoctorHolder(inflater.inflate(R.layout.view_doctor_list_item, parent, false), glideManager)
    }


    override fun onBindViewHolder(holder: DoctorHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DoctorHolder(itemView: View, private val glideManager: GlideManager) : RecyclerView.ViewHolder(itemView) {

        fun bind(doctor: Doctor) = with(itemView){
            tv_doctor_name.text = doctor.name
            tv_address.text = doctor.address
            glideManager.setDoctorId(doctor.id).loadDoctorImageInto(iv_doctor_avatar)
        }
    }

    companion object {
    val itemDiff = object : DiffUtil.ItemCallback<Doctor>() {

        override fun areItemsTheSame(old: Doctor, new: Doctor): Boolean {
            return old.id == new.id
        }

        override fun areContentsTheSame(old: Doctor, new: Doctor): Boolean {
            return old == new
        }
    }
}
}
package cn.rubintry.rtmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.rubintry.rtmanager.db.CustomRuntimeInfo
import cn.rubintry.rtmanager.R
import com.blankj.utilcode.util.SpanUtils

class RuntimeListAdapter(var dataList: List<CustomRuntimeInfo>, val onItemClickListener: (item: CustomRuntimeInfo) -> Unit) : RecyclerView.Adapter<RuntimeListAdapter.RuntimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuntimeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_runtime , parent , false)
        return RuntimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RuntimeViewHolder, position: Int) {
        val item = dataList[position]
        SpanUtils.with(holder.tvRuntimeItem)
            .append("负责人：${item.principal}\n")
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#999999"))
            .append("地址：${item.host}")
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#999999"))
            .create()

        holder.tvRuntimeItem?.setOnClickListener {
            onItemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class RuntimeViewHolder(item: View) : RecyclerView.ViewHolder(item){
        var tvRuntimeItem: TextView ?= null
        init {
            tvRuntimeItem = item.findViewById(R.id.tv_runtime_item)
        }
    }
}
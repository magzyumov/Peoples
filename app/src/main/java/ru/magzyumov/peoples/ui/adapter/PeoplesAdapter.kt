package ru.magzyumov.peoples.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.magzyumov.peoples.R
import ru.magzyumov.peoples.data.entity.PeopleEntity
import ru.magzyumov.peoples.databinding.ItemPeopleBinding

class PeoplesAdapter(dogs: List<PeopleEntity>,
                     private val interaction: Interaction): RecyclerView.Adapter<PeoplesAdapter.PeopleHolder>() {

    private var peoples: MutableList<PeopleEntity> = mutableListOf()

    init {
        peoples.addAll(dogs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPeopleBinding = DataBindingUtil.inflate(inflater, R.layout.item_people, parent, false)
        return PeopleHolder(binding, interaction)
    }

    override fun getItemCount(): Int {
        return peoples.size
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        holder.bind(people = peoples[position])
    }

    fun swap(dogs: List<PeopleEntity>) {
        val diffCallback = DiffCallback(peoples, dogs)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        peoples.clear()
        peoples.addAll(dogs)
        diffResult.dispatchUpdatesTo(this)
    }

    class PeopleHolder(binding: ItemPeopleBinding,
                       private val interaction: Interaction): RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemPeopleBinding = binding

        fun bind(people: PeopleEntity) {
            binding.people = people
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                interaction.onItemSelected(adapterPosition, people)
            }
        }
    }

    class DiffCallback(
        private val oldList: List<PeopleEntity>,
        private val newList: List<PeopleEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].email == newList[newItemPosition].email
                    && oldList[oldItemPosition].first_name == newList[newItemPosition].first_name
                    && oldList[oldItemPosition].last_name == newList[newItemPosition].last_name
                    && oldList[oldItemPosition].avatar == newList[newItemPosition].avatar
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PeopleEntity)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("avatar")
        fun loadImage(view: ImageView, image: String) {
            Glide.with(view.context)
                .load(image)
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .into(view)
        }
    }
}

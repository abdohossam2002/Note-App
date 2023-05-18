package com.example.mynotesapp

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_notes_list.*
import kotlinx.android.synthetic.main.noteticket.view.*

@Suppress("DEPRECATION")
class NotesListFragment : Fragment() {

    val listNotes = ArrayList<Note>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
       // listNotes.add(Note(1,"Good Job","It's So Hard To Learn Programing"))
        querySearch("%")

    }

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    fun  querySearch(noteTitle:String){
        var dbManager = DbManager(this!!.activity!!)
        val projection = arrayOf("ID","Title","Description")
        val selectionArgs = arrayOf(noteTitle)
        val cursor = dbManager.query(projection,"Title like ?",selectionArgs,"Title")
        if (cursor.moveToFirst()){
            listNotes.clear()
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                listNotes.add(Note(id,title,description))
            }while (cursor.moveToNext())
        }
        var mtAdapter = MyNotesAdapter(this!!.activity!!,listNotes)
        lvNotes.adapter = mtAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.noteslist_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.addNotesBu -> {
                view?.findNavController()
                    ?.navigate(R.id.action_notesListFragment_to_addNoteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter{
        var listNotesAdpter = ArrayList<Note>()
        var context:Context?=null
        constructor(context: Context,listNotesAdpter:ArrayList<Note>):super(){
            this.context=context
            this.listNotesAdpter=listNotesAdpter
        }

        override fun getCount(): Int {
            return listNotesAdpter.size
        }

        override fun getItem(position: Int): Any {
            return listNotesAdpter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.noteticket,null)
            val note = listNotesAdpter[position]
            myView.tvTitle.text=note.nodeTitle
            myView.tvDes.text=note.nodeDes
            //delete
            myView.ivDelete.setOnClickListener {
                val dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(note.nodeID.toString())
                dbManager.delete("ID=?",selectionArgs)
                querySearch("%")
            }
            myView.ivEdit.setOnClickListener{
                goToUpdate(note     )
            }
            return myView
        }
        fun goToUpdate(note:Note){
            var bundel = Bundle()
            bundel.putInt("ID",note.nodeID!!)
            bundel.putString("Title",note.nodeTitle!!)
            bundel.putString("Description",note.nodeDes!!)
            view!!.findNavController().navigate(R.id.action_notesListFragment_to_addNoteFragment,bundel)
        }
    }
}
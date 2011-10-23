package net.nielinjie.beautyFans

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import android.net.Uri
import nielinjie.util.data._
import android.util.Log
import android.provider.ContactsContract
import android.content.Context
import org.scalaquery.ql.TypeMapper._

class MainActivity extends Activity {
  var db:Repository=null
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    db=new Repository
    Log.i("MainActivity", "onCreate")
    setContentView(new TextView(this) {
      setText(loadData)
    })
  }


  def loadData: String = {
    //    helpersWrap(managedQuery(Uri.parse("content://net.nielinjie.beautyfans/sites"), null, null, null, null)).applyTo {
    //      c =>
    //        c.moveToNext()
    //        c.getString(0)
    //    }
    //class HelperWrapped[A](val obj:A)

    import Helper2._

//    val cur = managedQuery(Uri.parse("content://net.nielinjie.beautyfans/sites"), null, null, null, null)
//    (cur).applyTo({
//      c =>
//        c.moveToNext()
//        c.getString(0)
//    })
  db.Sites.list.mkString(" ")
  }

}


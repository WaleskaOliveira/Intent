package br.edu.ifsp.scl.ads.pdm.intents

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.content.SyncRequest
import android.net.Uri
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.pdm.intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
     companion object Constantes{
         const val PARAMETRO_EXTRA = "PARAMETRO_EXTRA"

     }

    private lateinit var parl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = this@MainActivity.javaClass.simpleName
        }

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getStringExtra(PARAMETRO_EXTRA)?.let {
                        amb.parametroTv.text = it
                    }
                }
            }

        amb.entrarParametroBt.setOnClickListener {
//            val parametroIntent = Intent(this, ParametroActivity::class.java)
//            parametroIntent.putExtra(PARAMETRO_EXTRA,amb.parametroTv.text.toString())
//            startActivityForResult(parametroIntent, PARAMETRO_REQUEST_CODE)

            Intent("MINHA_ACTION_PARA_PROXIMA_TELA").apply {
                amb.parametroTv.text.toString().let{
                    putExtra(PARAMETRO_EXTRA, it)
                }
                parl.launch(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.viewMi -> {
                Uri.parse(amb.parametroTv.text.toString()).let{
                    Intent(ACTION_DIAL).apply{
                        data = "tel: ${amb.parametroTv.text"
                    }
                }
                true

            }
            R.id.callMi -> { true }
            R.id.dialMi -> { true }
            R.id.pickMi -> { true }
            R.id.chooseMi -> { true }
            else -> { false }
        }
        return super.onOptionsItemSelected(item)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == PARAMETRO_REQUEST_CODE && resultCode == RESULT_OK){
//            data?.getStringExtra(PARAMETRO_EXTRA)?.let{ retorno ->
//                amb.parametroTv.text = retorno
//            }
//        }
//    }
}
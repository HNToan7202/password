package com.example.yourpass

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import app.keemobile.kotpass.constants.BasicField
import app.keemobile.kotpass.cryptography.EncryptedValue
import app.keemobile.kotpass.database.Credentials
import app.keemobile.kotpass.database.KeePassDatabase
import app.keemobile.kotpass.database.encode
import app.keemobile.kotpass.database.modifiers.modifyMeta
import app.keemobile.kotpass.models.Entry
import app.keemobile.kotpass.models.EntryFields
import app.keemobile.kotpass.models.EntryValue
import app.keemobile.kotpass.models.Meta
import com.example.yourpass.data.entity.FSAuthority.Companion.INTERNAL_FS_AUTHORITY
import com.example.yourpass.data.entity.FileDescriptor
import com.example.yourpass.data.repository.file.FSOptions
import com.example.yourpass.data.repository.file.OnConflictStrategy
import com.example.yourpass.data.repository.file.RegularFileSystemProvider
import com.example.yourpass.ui.theme.YourPassTheme
import com.example.yourpass.utils.FileUtils
import com.example.yourpass.utils.FileUtils.createPath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import java.util.concurrent.atomic.AtomicReference

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    fun createDbFile(): FileDescriptor {
        val name = "toandb"
        val path = createPath(
            parentPath = this.filesDir.absolutePath,
            name = "$name.kdbx"
        )

        return FileDescriptor(
            fsAuthority = INTERNAL_FS_AUTHORITY,
            path = path,
            uid = path,
            name = FileUtils.getFileNameFromPath(path),
            isDirectory = false,
            isRoot = false,
            modified = null
        )
    }

    suspend fun createDb(context: Context) {
        val credentials = Credentials.from(EncryptedValue.fromString("Ht@12354!"))

        // 1. Tạo DB trống
        val rawDb = KeePassDatabase.Ver4x.create(
            rootName = "rootname",
            meta = Meta(recycleBinEnabled = true),
            credentials = credentials
        )


        // 2. Thêm entry mới
        val newEntry = Entry(
            uuid = UUID.randomUUID(),
            overrideUrl = "12345",
            fields = EntryFields.of(
                BasicField.Title() to EntryValue.Plain("pass tele"),
                BasicField.Password() to EntryValue.Plain("Ht@12354"),
                BasicField.UserName() to EntryValue.Plain("toan3"),
            )
        )

        val newEntry2 = Entry(
            uuid = UUID.randomUUID(),
            overrideUrl = "12345",
            fields = EntryFields.of(
                BasicField.Title() to EntryValue.Plain("pass fb"),
                BasicField.Password() to EntryValue.Plain("Ht@12354"),
                BasicField.UserName() to EntryValue.Plain("toan2"),
            )
        )

        val newEntry3 = Entry(
            uuid = UUID.randomUUID(),
            overrideUrl = "12345",
            fields = EntryFields.of(
                BasicField.Title() to EntryValue.Plain("pass zalo"),
                BasicField.Password() to EntryValue.Plain("Ht@12354"),
                BasicField.UserName() to EntryValue.Plain("toan1"),
            )
        )

        var listEntry = mutableListOf<Entry>()
        listEntry.add(newEntry)
        listEntry.add(newEntry2)
        listEntry.add(newEntry3)
        val oldGroup = rawDb.content.group
        val updatedGroup = oldGroup.copy(entries = oldGroup.entries + listEntry)
        val updatedDb = rawDb.copy(content = rawDb.content.copy(group = updatedGroup))

        // 3. Cập nhật metadata
        val entryTemplatesGroup = UUID.randomUUID()
        val finalDb = updatedDb.modifyMeta {
            copy(
                recycleBinEnabled = true,
                recycleBinUuid = UUID.randomUUID(),
                entryTemplatesGroup = entryTemplatesGroup
            )
        }

        val database = AtomicReference(finalDb)
        val fsOptions = FSOptions.DEFAULT
        val fileDB = createDbFile() // đảm bảo hàm này trả FileDescriptor hợp lệ
        val updatedFile = fileDB.copy(modified = System.currentTimeMillis())
        val fsProvider = RegularFileSystemProvider(context, INTERNAL_FS_AUTHORITY)

        val lock = Mutex()
        lock.withLock {
            val outResult = fsProvider.openFileForWrite(
                updatedFile,
                OnConflictStrategy.CANCEL,
                fsOptions
            )

            if (outResult.isSucceeded) {
                val out = outResult.obj!!
                database.get().encode(out)
                Log.d("Kotpass", "Database written successfully: ${updatedFile.path}")
            } else {
                Log.e("Kotpass", "Failed to open file: ${outResult.error?.message}")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            createDb(this@MainActivity)
        }
        enableEdgeToEdge()
        setContent {
            YourPassTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding))
                    Navigation()
                }
            }
        }
    }
}

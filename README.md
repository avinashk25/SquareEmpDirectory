Index: app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt
--- a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt	
@@ -1,5 +1,8 @@
 package com.project.squareempdirectory.retrofit.model
 
+import androidx.room.ColumnInfo
+import androidx.room.Entity
+import androidx.room.PrimaryKey
 import com.google.gson.annotations.SerializedName
 import com.project.squareempdirectory.BaseApplication
 import com.project.squareempdirectory.R
@@ -16,33 +19,43 @@
  * Individual Employee information to be displayed.
  */
 
+@Entity(tableName = "employee")
 data class EmployeesListItem(
 
     @field:SerializedName("uuid")
+    @PrimaryKey @ColumnInfo(name = "uuid")
     val uuid: String,
 
     @field:SerializedName("full_name")
+    @ColumnInfo(name = "fullName")
     val fullName: String,
 
     @field:SerializedName("phone_number")
+    @ColumnInfo(name = "phoneNumber")
     val phoneNumber: String,
 
     @field:SerializedName("email_address")
+    @ColumnInfo(name = "emailAddress")
     val emailAddress: String,
 
     @field:SerializedName("employee_type")
+    @ColumnInfo(name = "employeeType")
     val employeeType: String,
 
     @field:SerializedName("biography")
+    @ColumnInfo(name = "biography")
     val biography: String,
 
     @field:SerializedName("team")
+    @ColumnInfo(name = "team")
     val team: String,
 
     @field:SerializedName("photo_url_large")
+    @ColumnInfo(name = "photoUrlLarge")
     val photoUrlLarge: String,
 
     @field:SerializedName("photo_url_small")
+    @ColumnInfo(name = "photoUrlSmall")
     val photoUrlSmall: String,
 )
 
Index: app/build.gradle
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/build.gradle b/app/build.gradle
--- a/app/build.gradle	
+++ b/app/build.gradle	
@@ -48,6 +48,8 @@
     implementation 'androidx.appcompat:appcompat:1.4.2'
     implementation 'com.google.android.material:material:1.6.1'
     implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
+    implementation 'androidx.navigation:navigation-fragment-ktx:2.5.1'
+    implementation 'androidx.navigation:navigation-ui-ktx:2.5.1'
     testImplementation 'junit:junit:4.13.2'
     androidTestImplementation 'androidx.test.ext:junit:1.1.3'
     androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
@@ -78,6 +80,17 @@
     implementation "com.squareup.okhttp3:okhttp-urlconnection:${okhttpVersion}"
     implementation "com.squareup.okhttp3:logging-interceptor:${okhttpVersion}"
 
+    def room_version = "2.4.3"
+
+    implementation("androidx.room:room-runtime:$room_version")
+    annotationProcessor("androidx.room:room-compiler:$room_version")
+    // To use Kotlin annotation processing tool (kapt)
+    kapt("androidx.room:room-compiler:$room_version")
+    // optional - Kotlin Extensions and Coroutines support for Room
+    implementation("androidx.room:room-ktx:$room_version")
+    // optional - RxJava2 support for Room
+    implementation("androidx.room:room-rxjava2:$room_version")
+
     implementation "com.google.dagger:hilt-android:2.42"
     kapt "com.google.dagger:hilt-compiler:2.42"
 
@@ -95,6 +108,8 @@
     testImplementation "androidx.test:runner:1.4.0"
     testImplementation "androidx.test:core-ktx:1.4.0"
     testImplementation "androidx.test:rules:1.4.0"
+    // optional - Test helpers
+    testImplementation("androidx.room:room-testing:$room_version")
 
     testImplementation("com.google.dagger:hilt-android-testing:2.35")
     kaptTest("com.google.dagger:hilt-android-compiler:2.42")
Index: app/src/test/java/com/project/squareempdirectory/EmployeeListViewModelUnitTest.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/test/java/com/project/squareempdirectory/EmployeeListViewModelUnitTest.kt b/app/src/test/java/com/project/squareempdirectory/EmployeeListViewModelUnitTest.kt
--- a/app/src/test/java/com/project/squareempdirectory/EmployeeListViewModelUnitTest.kt	
+++ b/app/src/test/java/com/project/squareempdirectory/EmployeeListViewModelUnitTest.kt	
@@ -1,6 +1,7 @@
 package com.project.squareempdirectory
 
 import androidx.arch.core.executor.testing.InstantTaskExecutorRule
+import com.project.squareempdirectory.database.repository.EmployeeRepository
 import com.project.squareempdirectory.retrofit.model.EmployeesListItem
 import com.project.squareempdirectory.retrofit.model.SquareEmployeeResponse
 import com.project.squareempdirectory.retrofit.model.SquareEmployeeService
@@ -32,12 +33,15 @@
     @RelaxedMockK
     lateinit var service: SquareEmployeeService
 
+    @RelaxedMockK
+    lateinit var repository: EmployeeRepository
+
     private lateinit var viewModel: EmployeeListViewModel
 
     @Before
     fun setUp() {
         MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
-        viewModel = EmployeeListViewModel(service)
+        viewModel = EmployeeListViewModel(service, repository)
     }
 
     @After
Index: app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareAPIService.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareAPIService.kt b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareAPIService.kt
--- a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareAPIService.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareAPIService.kt	
@@ -5,7 +5,7 @@
 import dagger.Module
 import dagger.Provides
 import dagger.hilt.InstallIn
-import dagger.hilt.android.components.ViewModelComponent
+import dagger.hilt.components.SingletonComponent
 import okhttp3.OkHttpClient
 import retrofit2.Retrofit
 import retrofit2.converter.gson.GsonConverterFactory
@@ -15,7 +15,7 @@
  * Provides retrofit client instance to consume http APIs.
  */
 
-@InstallIn(ViewModelComponent::class)
+@InstallIn(SingletonComponent::class)
 @Module
 class SquareAPIService {
 
Index: .idea/compiler.xml
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/.idea/compiler.xml b/.idea/compiler.xml
--- a/.idea/compiler.xml	
+++ b/.idea/compiler.xml	
@@ -1,6 +1,6 @@
 <?xml version="1.0" encoding="UTF-8"?>
 <project version="4">
   <component name="CompilerConfiguration">
-    <bytecodeTargetLevel target="1.8" />
+    <bytecodeTargetLevel target="11" />
   </component>
 </project>
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/database/repository/EmployeeRepository.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/database/repository/EmployeeRepository.kt b/app/src/main/java/com/project/squareempdirectory/database/repository/EmployeeRepository.kt
new file mode 100644
--- /dev/null	
+++ b/app/src/main/java/com/project/squareempdirectory/database/repository/EmployeeRepository.kt	
@@ -0,0 +1,35 @@
+package com.project.squareempdirectory.database.repository
+
+import androidx.annotation.WorkerThread
+import com.project.squareempdirectory.database.dao.EmployeeDao
+import com.project.squareempdirectory.retrofit.model.EmployeesListItem
+import com.project.squareempdirectory.retrofit.model.SquareEmployeeService
+import kotlinx.coroutines.flow.Flow
+import kotlinx.coroutines.flow.count
+import kotlinx.coroutines.flow.first
+import javax.inject.Inject
+import javax.inject.Singleton
+
+@Singleton
+class EmployeeRepository @Inject constructor(private val service: SquareEmployeeService, private val employeeDao: EmployeeDao) {
+
+    val employeeList: Flow<List<EmployeesListItem>> = employeeDao.getEmployeeList()
+
+    @WorkerThread
+    suspend fun insert(employeeEntity: EmployeesListItem) {
+        employeeDao.insert(employeeEntity)
+    }
+
+    suspend fun getEmployeesList() : List<EmployeesListItem> {
+        var list : List<EmployeesListItem> = emptyList()
+        //if (employeeList.first().isEmpty()) {
+            list = service.getEmployeesList().employeesList.sortedBy { it.fullName.lowercase() }
+        //}
+        if (list.isNotEmpty()) {
+            for (items in list) {
+                insert(items)
+            }
+        }
+        return list
+    }
+}
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/database/db/EmployeeRoomDatabase.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/database/db/EmployeeRoomDatabase.kt b/app/src/main/java/com/project/squareempdirectory/database/db/EmployeeRoomDatabase.kt
new file mode 100644
--- /dev/null	
+++ b/app/src/main/java/com/project/squareempdirectory/database/db/EmployeeRoomDatabase.kt	
@@ -0,0 +1,34 @@
+package com.project.squareempdirectory.database.db
+
+import android.content.Context
+import androidx.room.Database
+import androidx.room.Room
+import androidx.room.RoomDatabase
+import com.project.squareempdirectory.database.dao.EmployeeDao
+import com.project.squareempdirectory.retrofit.model.EmployeesListItem
+
+@Database(entities = [EmployeesListItem::class], version = 1)
+abstract class EmployeeRoomDatabase : RoomDatabase() {
+
+    abstract fun employeeDao():EmployeeDao
+
+    companion object {
+
+        @Volatile
+        private var INSTANCE : EmployeeRoomDatabase? = null
+
+        fun getDatabase( context: Context) : EmployeeRoomDatabase {
+            return INSTANCE ?: synchronized(this) {
+                val instance = Room.databaseBuilder(
+                    context.applicationContext,
+                    EmployeeRoomDatabase::class.java,
+                    "employee_database"
+                )
+                    .fallbackToDestructiveMigration()
+                    .build()
+                INSTANCE = instance
+                instance
+            }
+        }
+    }
+}
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt b/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt
--- a/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt	
@@ -1,12 +1,9 @@
 package com.project.squareempdirectory.viewmodel
 
-import androidx.lifecycle.LiveData
-import androidx.lifecycle.MutableLiveData
-import androidx.lifecycle.ViewModel
-import androidx.lifecycle.viewModelScope
+import androidx.lifecycle.*
 import com.project.squareempdirectory.SingleLiveEvent
+import com.project.squareempdirectory.database.repository.EmployeeRepository
 import com.project.squareempdirectory.retrofit.model.EmployeesListItem
-import com.project.squareempdirectory.retrofit.model.SquareEmployeeService
 import dagger.hilt.android.lifecycle.HiltViewModel
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.launch
@@ -18,7 +15,7 @@
  */
 
 @HiltViewModel
-class EmployeeListViewModel @Inject constructor(private val service: SquareEmployeeService) : ViewModel() {
+class EmployeeListViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {
 
     // LiveData variable to track list of schools to be displayed on UI
     private val _items = MutableLiveData<List<EmployeesListItem>>()
@@ -32,6 +29,8 @@
     private val _isRefreshing = SingleLiveEvent<Boolean>()
     val isRefreshing: LiveData<Boolean> = _isRefreshing
 
+    val employeeList: LiveData<List<EmployeesListItem?>> = repository.employeeList.asLiveData()
+
     init {
         loadEmployeeList()
     }
@@ -43,7 +42,7 @@
     fun loadEmployeeList() {
         viewModelScope.launch(Dispatchers.IO ) {
             kotlin.runCatching {
-                service.getEmployeesList().employeesList.sortedBy { it.fullName.lowercase() }
+                repository.getEmployeesList()
                 //service.getEmptyEmployeesList().employeesList
                 //service.getMalformedEmployeesList().employeesList
             }.onFailure {
Index: app/src/main/java/com/project/squareempdirectory/database/dao/EmployeeDao.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/database/dao/EmployeeDao.kt b/app/src/main/java/com/project/squareempdirectory/database/dao/EmployeeDao.kt
new file mode 100644
--- /dev/null	
+++ b/app/src/main/java/com/project/squareempdirectory/database/dao/EmployeeDao.kt	
@@ -0,0 +1,23 @@
+package com.project.squareempdirectory.database.dao
+
+import androidx.room.Dao
+import androidx.room.Insert
+import androidx.room.OnConflictStrategy
+import androidx.room.Query
+import com.project.squareempdirectory.retrofit.model.EmployeesListItem
+import kotlinx.coroutines.flow.Flow
+
+@Dao
+interface EmployeeDao {
+
+    // The flow always holds/caches latest version of data. Notifies its observers when the
+    // data has changed.
+    @Query("SELECT * FROM employee ORDER BY uuid ASC")
+    fun getEmployeeList(): Flow<List<EmployeesListItem>>
+
+    @Insert(onConflict = OnConflictStrategy.IGNORE)
+    suspend fun insert(employee: EmployeesListItem)
+
+    @Query("DELETE FROM employee")
+    suspend fun deleteAll()
+}
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/database/provider/DatabaseProvider.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/database/provider/DatabaseProvider.kt b/app/src/main/java/com/project/squareempdirectory/database/provider/DatabaseProvider.kt
new file mode 100644
--- /dev/null	
+++ b/app/src/main/java/com/project/squareempdirectory/database/provider/DatabaseProvider.kt	
@@ -0,0 +1,20 @@
+package com.project.squareempdirectory.database.provider
+
+import android.content.Context
+import com.project.squareempdirectory.database.dao.EmployeeDao
+import com.project.squareempdirectory.database.db.EmployeeRoomDatabase
+import dagger.Module
+import dagger.Provides
+import dagger.hilt.InstallIn
+import dagger.hilt.android.qualifiers.ApplicationContext
+import dagger.hilt.components.SingletonComponent
+
+@InstallIn(SingletonComponent::class)
+@Module
+class DatabaseProvider {
+
+    @Provides
+    fun providesEmployeeDbDao(@ApplicationContext context: Context) : EmployeeDao {
+        return EmployeeRoomDatabase.getDatabase(context).employeeDao()
+    }
+}
\ No newline at end of file

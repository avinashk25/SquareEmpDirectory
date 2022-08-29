Index: app/src/main/AndroidManifest.xml
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml
--- a/app/src/main/AndroidManifest.xml	
+++ b/app/src/main/AndroidManifest.xml	
@@ -3,8 +3,6 @@
     xmlns:tools="http://schemas.android.com/tools"
     package="com.project.squareempdirectory">
 
-    <uses-permission android:name="android.permission.INTERNET" />
-
     <application
         android:name="com.project.squareempdirectory.BaseApplication"
         android:allowBackup="true"
@@ -17,6 +15,9 @@
         android:theme="@style/Theme.SquareEmpDirectory"
         tools:targetApi="31">
         <activity
+            android:name=".EmployeeDetailsActivity"
+            android:exported="false" />
+        <activity
             android:name=".MainActivity"
             android:exported="true">
             <intent-filter>
@@ -27,4 +28,6 @@
         </activity>
     </application>
 
+    <uses-permission android:name="android.permission.INTERNET" />
+
 </manifest>
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt
--- a/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/retrofit/model/SquareEmployeeResponse.kt	
@@ -3,6 +3,7 @@
 import com.google.gson.annotations.SerializedName
 import com.project.squareempdirectory.BaseApplication
 import com.project.squareempdirectory.R
+import java.io.Serializable
 
 /**
  * Endpoint response is received as list of employees.
@@ -44,7 +45,7 @@
 
     @field:SerializedName("photo_url_small")
     val photoUrlSmall: String,
-)
+) : Serializable
 
 /**
  * Enum class to display type of employment.
Index: app/src/main/java/com/project/squareempdirectory/ui/EmployeeListFragment.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListFragment.kt b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListFragment.kt
--- a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListFragment.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListFragment.kt	
@@ -1,11 +1,13 @@
 package com.project.squareempdirectory.ui
 
+import android.content.Intent
 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import androidx.fragment.app.viewModels
+import com.project.squareempdirectory.EmployeeDetailsActivity
 import com.project.squareempdirectory.databinding.EmployeeListFragmentBinding
 import com.project.squareempdirectory.retrofit.model.EmployeesListItem
 import com.project.squareempdirectory.viewmodel.EmployeeListViewModel
@@ -18,6 +20,10 @@
 @AndroidEntryPoint
 class EmployeeListFragment : Fragment() {
 
+    companion object {
+        const val employeeObject = "employee_details"
+    }
+
     private lateinit var binding: EmployeeListFragmentBinding
     private val viewModel : EmployeeListViewModel by viewModels()
 
@@ -32,6 +38,7 @@
         binding.viewModel = viewModel
         viewModel.items.observe(viewLifecycleOwner, ::onEmployeeListItemLoaded)
         viewModel.isEmpty.observe(viewLifecycleOwner, ::updateEmptyListState)
+        viewModel.itemClick.observe(viewLifecycleOwner, ::onEmployeeItemClick)
         return binding.root
     }
 
@@ -53,4 +60,12 @@
             binding.isEmpty = isListEmpty
         }
     }
+
+    private fun onEmployeeItemClick(employeesListItem: EmployeesListItem) {
+        val intent = Intent(context, EmployeeDetailsActivity::class.java)
+        val bundle = Bundle()
+        bundle.putSerializable(employeeObject, employeesListItem)
+        intent.putExtras(bundle)
+        startActivity(intent)
+    }
 }
\ No newline at end of file
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
Index: app/src/main/java/com/project/squareempdirectory/ui/EmployeeListAdapter.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListAdapter.kt b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListAdapter.kt
--- a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListAdapter.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeListAdapter.kt	
@@ -12,7 +12,7 @@
  * A RecyclerView.Adapter populating the screen with a list of employees fetched through viewModel using retrofit Http API.
  */
 
-class EmployeeListAdapter(var items: List<EmployeesListItem> = emptyList()) : RecyclerView.Adapter<EmployeeListViewHolder>(){
+class EmployeeListAdapter(var items: List<EmployeesListItem> = emptyList(), val adapterOnClick : (EmployeesListItem) -> Unit) : RecyclerView.Adapter<EmployeeListViewHolder>(){
 
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
         val binding = EmployeeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
@@ -21,6 +21,7 @@
 
     override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
         holder.binding.employeeDetails = items.elementAtOrNull(position)
+        holder.binding.cardView.setOnClickListener { adapterOnClick(items.elementAt(position)) }
     }
 
     override fun getItemCount(): Int {
Index: app/src/main/java/com/project/squareempdirectory/ui/EmployeeDataBinding.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeDataBinding.kt b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeDataBinding.kt
--- a/app/src/main/java/com/project/squareempdirectory/ui/EmployeeDataBinding.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/ui/EmployeeDataBinding.kt	
@@ -12,10 +12,10 @@
  * Binding methods added to update UI items from layout files.
  */
 
-@BindingAdapter("items")
-fun RecyclerView.addItems(items : List<EmployeesListItem>?) {
+@BindingAdapter("items" , "adapterOnClick")
+fun RecyclerView.addItems(items : List<EmployeesListItem>?, adapterOnClick : (EmployeesListItem) -> Unit) {
     if (adapter == null) {
-        val fragmentListAdapter = EmployeeListAdapter(items ?: emptyList())
+        val fragmentListAdapter = EmployeeListAdapter(items ?: emptyList(), adapterOnClick)
         adapter = fragmentListAdapter
         addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
     } else {
Index: app/src/main/java/com/project/squareempdirectory/EmployeeDetailsActivity.kt
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/EmployeeDetailsActivity.kt b/app/src/main/java/com/project/squareempdirectory/EmployeeDetailsActivity.kt
new file mode 100644
--- /dev/null	
+++ b/app/src/main/java/com/project/squareempdirectory/EmployeeDetailsActivity.kt	
@@ -0,0 +1,28 @@
+package com.project.squareempdirectory
+
+import androidx.appcompat.app.AppCompatActivity
+import android.os.Bundle
+import androidx.databinding.DataBindingUtil
+import com.project.squareempdirectory.databinding.ActivityEmployeeDetailsBinding
+import com.project.squareempdirectory.ui.EmployeeDetailsFragment
+import dagger.hilt.android.AndroidEntryPoint
+
+@AndroidEntryPoint
+class EmployeeDetailsActivity : AppCompatActivity() {
+
+    private lateinit var binding : ActivityEmployeeDetailsBinding
+    override fun onCreate(savedInstanceState: Bundle?) {
+        super.onCreate(savedInstanceState)
+        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_details)
+        binding.lifecycleOwner = this
+        val extras = intent.extras
+
+        val bundle = Bundle().apply {
+            putSerializable(EmployeeDetailsFragment.employeeObject, extras?.getSerializable(EmployeeDetailsFragment.employeeObject))
+        }
+
+        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
+            EmployeeDetailsFragment::class.java, bundle
+        ).commitNow()
+    }
+}
\ No newline at end of file
Index: .idea/misc.xml
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/.idea/misc.xml b/.idea/misc.xml
--- a/.idea/misc.xml	
+++ b/.idea/misc.xml	
@@ -3,13 +3,15 @@
   <component name="DesignSurface">
     <option name="filePathToZoomLevelMap">
       <map>
+        <entry key="app/src/main/res/layout/activity_employee_details.xml" value="0.190625" />
         <entry key="app/src/main/res/layout/activity_main.xml" value="0.32518115942028986" />
+        <entry key="app/src/main/res/layout/employee_details_fragment.xml" value="0.2" />
         <entry key="app/src/main/res/layout/employee_list_fragment.xml" value="0.19479166666666667" />
         <entry key="app/src/main/res/layout/employee_list_item.xml" value="0.2164855072463768" />
       </map>
     </option>
   </component>
-  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_8" default="true" project-jdk-name="1.8" project-jdk-type="JavaSDK">
+  <component name="ProjectRootManager" version="2" languageLevel="JDK_11" project-jdk-name="1.8" project-jdk-type="JavaSDK">
     <output url="file://$PROJECT_DIR$/build/classes" />
   </component>
   <component name="ProjectType">
Index: app/src/main/res/layout/employee_list_fragment.xml
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/res/layout/employee_list_fragment.xml b/app/src/main/res/layout/employee_list_fragment.xml
--- a/app/src/main/res/layout/employee_list_fragment.xml	
+++ b/app/src/main/res/layout/employee_list_fragment.xml	
@@ -38,6 +38,7 @@
                 android:layout_height="match_parent"
                 android:orientation="vertical"
                 app:items="@{viewModel.items}"
+                app:adapterOnClick="@{viewModel.adapterOnClick}"
                 app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                 tools:listitem="@layout/employee_list_item" />
 
Index: app/src/main/res/layout/activity_employee_details.xml
===================================================================
diff --git a/app/src/main/res/layout/activity_employee_details.xml b/app/src/main/res/layout/activity_employee_details.xml
new file mode 100644
--- /dev/null	
+++ b/app/src/main/res/layout/activity_employee_details.xml	
@@ -0,0 +1,14 @@
+<?xml version="1.0" encoding="utf-8"?>
+<layout xmlns:android="http://schemas.android.com/apk/res/android">
+
+    <merge
+        android:id="@+id/container"
+        android:layout_width="match_parent"
+        android:layout_height="match_parent">
+
+        <FrameLayout
+            android:id="@+id/fragment_container"
+            android:layout_width="match_parent"
+            android:layout_height="match_parent"/>
+    </merge>
+</layout>
\ No newline at end of file
Index: app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt b/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt
--- a/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt	
+++ b/app/src/main/java/com/project/squareempdirectory/viewmodel/EmployeeListViewModel.kt	
@@ -32,6 +32,9 @@
     private val _isRefreshing = SingleLiveEvent<Boolean>()
     val isRefreshing: LiveData<Boolean> = _isRefreshing
 
+    private val _itemClick = MutableLiveData<EmployeesListItem>()
+    val itemClick : LiveData<EmployeesListItem> = _itemClick
+
     init {
         loadEmployeeList()
     }
@@ -55,4 +58,7 @@
             _isRefreshing.postValue(false)
         }
     }
+
+    val adapterOnClick : (EmployeesListItem) -> Unit  = {
+        _itemClick.value = it}
 }
\ No newline at end of file

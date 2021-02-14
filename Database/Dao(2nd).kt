import androidx.room.*
import com.onepos.posandroidv2.database.entity.Category


@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg dataEntities: Category)

    @Query("SELECT * FROM category WHERE is_delete=0 and is_active=1 ")
    fun getAll():List<Category>?

    @Query("SELECT * FROM category WHERE is_delete=0 and is_active=1 LIMIT 1")
    fun getFirst():Category

    @Delete
    fun delete(category: Category)
}
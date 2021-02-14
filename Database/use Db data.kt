import com.onepos.posandroidv2.database.repository.CategoryRepo

val repo= CategoryRepo.getInstance(context)
var all=repo.getall()

val repo= ItemRepo.getInstance(context)
val repo2= CategoryRepo.getInstance(context)
var first_cat=repo2.getfirst()
var all= first_cat.id?.let { repo.getAllById(it)
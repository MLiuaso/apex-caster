package bitter.apexcaster.info.data.repository

data class ApexWorkoutPlan(val id:Long,val name:String,val type:String,val level:String,val minutes:Int,val exercises:Int,val imageUrl:String)

object ApexWorkoutCatalog {
    val workouts = listOf(
        ApexWorkoutPlan(1,"Neighborhood Power Walk","Cardio","Beginner",18,5,"https://images.unsplash.com/photo-1538805060514-97d9cc17730c?w=900"),
        ApexWorkoutPlan(2,"Everyday Strength Circuit","Strength","Beginner",22,6,"https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=900"),
        ApexWorkoutPlan(3,"Mobility Reset","Mobility","Beginner",16,5,"https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=900"),
        ApexWorkoutPlan(4,"Tempo Run Builder","Cardio","Intermediate",30,7,"https://images.unsplash.com/photo-1517963879433-6ad2b056d712?w=900"),
        ApexWorkoutPlan(5,"Core and Carry","Strength","Intermediate",25,6,"https://images.unsplash.com/photo-1518611012118-696072aa579a?w=900"),
        ApexWorkoutPlan(6,"Low Impact Intervals","Cardio","Intermediate",24,8,"https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=900"),
        ApexWorkoutPlan(7,"Desk Break Recharge","Mobility","Beginner",12,4,"https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=900"),
        ApexWorkoutPlan(8,"Total Body Challenge","Strength","Advanced",35,8,"https://images.unsplash.com/photo-1549060279-7e168fcee0c2?w=900"),
        ApexWorkoutPlan(9,"Recovery Flow","Recovery","Beginner",15,5,"https://images.unsplash.com/photo-1506126613408-eca07ce68773?w=900"),
        ApexWorkoutPlan(10,"Fast Finish HIIT","Cardio","Advanced",20,7,"https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=900")
    )
}

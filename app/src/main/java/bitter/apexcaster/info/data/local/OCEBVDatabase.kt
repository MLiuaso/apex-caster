package bitter.apexcaster.info.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.local.dao.OCEBVArticleDao
import bitter.apexcaster.info.data.local.dao.OCEBVWorkoutDao
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity

@Database(
    entities = [OCEBVWorkoutEntity::class, OCEBVArticleEntity::class],
    version = 10,
    exportSchema = false
)
abstract class OCEBVDatabase : RoomDatabase() {
    abstract fun workoutDao(): OCEBVWorkoutDao
    abstract fun articleDao(): OCEBVArticleDao

    companion object {
        @Volatile
        private var INSTANCE: OCEBVDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): OCEBVDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            OCEBVDatabase::class.java,
                            "ocebv_database"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(
                                SportaraDatabaseCallback(scope)
                            )
                            .build()
                    INSTANCE = instance
                    instance
                }
        }
    }

    private class SportaraDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    if (database.articleDao().getArticleCount() == 0) {
                        populateDatabase(database.articleDao())
                    }

                }
            }
        }

        suspend fun populateDatabase(articleDao: OCEBVArticleDao) {
            val articles =
                listOf(
                    OCEBVArticleEntity(
                        title = "The Art of Running",
                        subtitle =
                            "Discover how proper form and consistent pacing can transform your daily run into a meditation in motion.",
                        content =
                            """
                            Running is often perceived as a simple act of putting one foot in front of the other, but true runners know it is a complex interplay of biomechanics, endurance, and mental fortitude. By mastering your stride, you not only prevent injury but also unlock a new level of efficiency that makes running feel effortless. It starts with posture: keep your head up, gaze forward, and shoulders relaxed away from your ears. Your arms should swing naturally at your sides, not crossing your body, which can waste energy and throw off your balance.

                            Foot strike is another critical component. Aim to land mid-foot directly under your center of gravity rather than overstriding and landing hard on your heel. This reduces the braking force with each step and minimizes impact on your joints. Cadence, or steps per minute, also matters; a higher cadence (around 170-180 spm) generally puts less stress on the legs.

                            But running is as much mental as it is physical. It provides a unique space for solitude and reflection. Consistency is key—start slow, build your aerobic base, and listen to your body's signals. Whether you're training for a marathon or just jogging around the block to clear your head, the art of running lies in finding joy in the movement itself. Embrace the rhythm of your breath and the pavement beneath your feet.
                        """.trimIndent(),
                        imageUrl =
                            "https://www.runsociety.com/wp-content/uploads/2019/10/run-alone-3.jpg",
                        category = "Running"
                    ),
                    OCEBVArticleEntity(
                        title = "Swimming Techniques 101",
                        subtitle =
                            "Unlock the secrets of hydrodynamics to glide through the water with less effort and more speed.",
                        content =
                            """
                            Swimming is a full-body workout that imposes a unique challenge: water resistance. To swim fast and efficiently, you must become a student of hydrodynamics. The primary goal is to minimize drag while maximizing propulsion. This starts with body position—imagine a steel rod running from your head to your toes. You want to stay horizontal on the surface of the water; if your hips sink, they act as an anchor, dragging you down and slowing you down.

                            The 'catch' phase of your stroke is where you generate the most power. Reach forward, anchor your hand in the water, and pull your body past your hand, keeping your elbow high. It’s not about churning the water; it’s about holding onto it. Your kick should be rhythmic and originate from the hips, not the knees, acting like a flutter to keep your body balanced rather than just driving you forward.

                            Breathing is often the biggest hurdle for beginners. The key is to exhale continuously while your face is underwater so that you only need to inhale when you turn your head. This keeps your stroke continuous and prevents the 'stop-start' momentum that exhausts many swimmers. With patience and focus on technique, swimming transforms from a struggle for air into a graceful, meditative, and powerful exercise.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1530549387789-4c1017266635?auto=format&fit=crop&w=800&q=80",
                        category = "Swimming"
                    ),
                    OCEBVArticleEntity(
                        title = "Cycling Endurance",
                        subtitle =
                            "Strategies to build stamina, conquer long distances, and enjoy the ride without burning out.",
                        content =
                            """
                            Building endurance on the bike is a journey that requires time, patience, and smart training. It's not just about pedaling harder; it's about pedaling longer and more efficiently. Start by increasing your mileage gradually—follow the 10% rule, where you increase your weekly distance by no more than 10% to avoid overuse injuries. Long, steady rides at a conversational pace (Zone 2) are the foundation of building a massive aerobic engine.

                            On the bike, efficiency is everything. specific attention to your cadence; grinding big gears fatigues your muscles quickly, while spinning a lighter gear at 80-90 RPM shifts the load to your cardiovascular system, which can endure much longer. Fit is also paramount; a proper bike fit ensures that you are utilizing your muscles effectively and prevents numbness or pain in your back, knees, and hands.

                            Nutrition plays a huge role in endurance cycling. 'Bonking' or running out of glycogen is every cyclist's nightmare. Fuel your body with complex carbs before long rides and aim to consume 30-60 grams of carbohydrates per hour during the ride. Hydration is equally critical—don't wait until you're thirsty. Finally, learn to read the terrain and conserve momentum. The joy of cycling comes from the freedom of the road; build your endurance, and that road becomes endless.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1541625602330-2277a4c46182?auto=format&fit=crop&w=800&q=80",
                        category = "Cycling"
                    ),
                    OCEBVArticleEntity(
                        title = "Yoga for Athletes",
                        subtitle =
                            "Why flexibility, balance, and breathwork are the ultimate recovery tools for high-performance sports.",
                        content =
                            """
                            For many athletes, yoga is often an afterthought, something to do on 'easy' days. However, it is a potent tool for enhancing performance and longevity. High-intensity sports like running, cycling, and weightlifting often involve repetitive movements that tighten specific muscle groups while neglecting others. Yoga restores balance to the body by stretching tight areas (like hamstrings and hip flexors) and strengthening underutilized stabilizers.

                            Beyond flexibility, yoga improves proprioception—your awareness of your body in space. This translates to better agility and coordination on the field or track. Core stability is another major benefit; a strong core transfers power more efficiently and protects the lower back from injury.

                            Perhaps the most underrated benefit is the training of the breath. Pranayama, or breath control, teaches athletes to regulate their nervous system. By learning to breathe deeply and rhythmically under the stress of a yoga pose, you train your mind to stay calm and focused under the pressure of competition. Managing performance anxiety and maintaining mental clarity are just as important as physical prep. Make yoga a regular part of your routine to stay agile, resilient, and mentally sharp.
                        """.trimIndent(),
                        imageUrl =
                            "https://i0.wp.com/skill-yoga.blog/wp-content/uploads/2021/08/best-workouts-for-athletes-1.jpg?w=1200&ssl=1",
                        category = "Flexibility"
                    ),
                    OCEBVArticleEntity(
                        title = "Nutrition for Peak Performance",
                        subtitle =
                            "A comprehensive guide to fueling your body before, during, and after intense physical activity.",
                        content =
                            """
                            Food is fuel, and the quality of that fuel dictates the quality of your performance. Understanding macronutrients is the first step. Carbohydrates are your body's high-octane gasoline, essential for high-intensity efforts. varied sources like oats, quinoa, and fruits provide sustained energy. Proteins are the building blocks for muscle repair and recovery; lean meats, eggs, and legumes should be staples in your diet. Healthy fats support long-duration low-intensity activity and hormone health.

                            Timing is just as important as composition. A pre-workout meal 2-3 hours before exercise should be carb-rich and moderate in protein effectively top off your glycogen stores. Post-workout, the 'anabolic window' isn't as narrow as once thought, but getting protein and carbs within an hour helps kickstart recovery and replenish energy stores.

                            Don't forget micronutrients. Vitamins and minerals from colorful vegetables play crucial roles in energy production and inflammation reduction. Hydration is also part of nutrition; electrolytes like sodium and potassium are lost in sweat and must be replaced to prevent cramping. Experiment with your nutrition during training, not on race day, to find what works best for your digestive system. Small dietary adjustments can lead to massive performance gains.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1490645935967-10de6ba17061?auto=format&fit=crop&w=800&q=80",
                        category = "Health"
                    ),
                    OCEBVArticleEntity(
                        title = "Strength Training Basics",
                        subtitle =
                            "Build a solid foundation of muscle to support your joints, prevent injury, and boost overall power.",
                        content =
                            """
                            Strength training is effectively the armor for your body. It is essential for everyone, from endurance athletes to office workers. It increases bone density, improves joint health, and boosts metabolism. For athletes, strength training corrects imbalances and builds the explosive power needed for speed and agility.

                            You don't need a complicated split routine to see results. Focus on the fundamental human movements: Push (push-ups, bench press), Pull (rows, pull-ups), Squat (goblet squats, back squats), Hinge (deadlifts, kettlebell swings), and Carry (farmer's walks). These compound movements recruit multiple muscle groups and give you the most bang for your buck.

                            Consistency and proper form are far more important than the amount of weight on the bar. Leave your ego at the door; mastering the technique of a squat with just your body weight is more impressive—and safer—than half-repping a heavy load. Aim for 2-3 full-body sessions a week. You'll verify that a stronger body is a more resilient body, capable of handling higher training volumes with less risk of injury.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?auto=format&fit=crop&w=800&q=80",
                        category = "Strength"
                    ),
                    OCEBVArticleEntity(
                        title = "Mental Toughness",
                        subtitle =
                            "Train your mind to push past limits, stay focused, and win the inner game of sports.",
                        content =
                            """
                            At the highest levels of sport, physical differences between athletes are often minimal. The edge goes to the one with the strongest mind. Mental toughness isn't about ignoring pain or suppressing emotions; it's about the ability to persevere through difficult circumstances, manage stress, and maintain focus when it matters most.

                            Visualization is a powerful tool used by elite athletes. By vividly imagining yourself executing a perfect race or game plan, you prime your brain's neural pathways for success. When you encounter that situation in reality, your body already 'knows' what to do. Positive self-talk is another key strategy; replacing 'I can't' with 'I am prepared' changes your physiological response to stress.

                            Resilience is built in practice. pushing yourself slightly outside your comfort zone triggers adaptation not just in your muscles, but in your mind. Learn to embrace the discomfort of hard effort. Set process goals (e.g., 'I will maintain my form') rather than outcome goals (e.g., 'I must win') to keep your focus on what you can control. Remember, your body can handle almost anything; it's your mind that you have to convince to keep going.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&w=800&q=80",
                        category = "Psychology"
                    ),
                    OCEBVArticleEntity(
                        title = "Hydration Strategy",
                        subtitle =
                            "Understand the science of hydration to prevent fatigue and maintain peak physical function.",
                        content =
                            """
                            Water is the most important nutrient for an athlete, yet it is often the most neglected. Your body is roughly 60% water, and it plays a vital role in temperature regulation, joint lubrication, and nutrient transport. Even mild dehydration of 2% body weight can significantly impair aerobic performance, cognitive function, and perceived effort.

                            A good hydration strategy starts before you exercise. Aim to start your workout well-hydrated—check your urine color; pale straw is the goal. During exercise, drink to thirst, but be mindful of your sweat rate. If you are a heavy sweater or exercising in heat, water alone may not be enough.

                            This is where electrolytes come in. Sodium, potassium, and magnesium are lost in sweat and are crucial for muscle contraction and nerve transmission. Replenishing them prevents cramping and hyponatremia. After your workout, weigh yourself; for every kilogram lost, drink 1.5 liters of fluid to rehydrate. Don't rely on thirst alone as an indicator, as it is often a delayed signal. Make hydration a daily habit, not just a workout tactic.
                        """.trimIndent(),
                        imageUrl =
                            "https://hips.hearstapps.com/hmg-prod/images/drinking-water-royalty-free-image-1627486254.jpg?crop=1.00xw:1.00xh;0,0&resize=1800:*",
                        category = "Health"
                    ),
                    OCEBVArticleEntity(
                        title = "HIIT Workouts Explained",
                        subtitle =
                            "Get maximum results in minimum time with High-Intensity Interval Training protocols.",
                        content =
                            """
                            High-Intensity Interval Training (HIIT) has taken the fitness world by storm, and for good reason. It involves alternating short bursts of near-maximal effort with fixed periods of less-intense activity or complete rest. A typical session might look like 30 seconds of sprinting followed by 30 seconds of walking, repeated for 15-20 minutes.

                            The beauty of HIIT lies in its efficiency. It provides robust cardiovascular benefits comparable to much longer steady-state workouts. Furthermore, it triggers Excess Post-exercise Oxygen Consumption (EPOC), or the 'afterburn effect,' meaning your body continues to burn calories at an elevated rate for hours after the workout as it returns to homeostasis.

                            HIIT is versatile—you can do it running, cycling, rowing, or with bodyweight exercises like burpees. However, because of its intensity, it places a high demand on the body. Proper warm-up is non-negotiable, and recovery is crucial. It is generally recommended to limit HIIT sessions to 2-3 times per week to prevent burnout and injury. If you're short on time but want serious results, HIIT is the ultimate tool in your arsenal.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1601422407692-ec4eeec1d9b3?auto=format&fit=crop&w=800&q=80",
                        category = "Cardio"
                    ),
                    OCEBVArticleEntity(
                        title = "Recovery Essentials",
                        subtitle =
                            "Why rest days are crucial for growth and how to optimize your downtime for better gains.",
                        content =
                            """
                            In a culture that glorifies 'the grind,' rest is often viewed as laziness. But the science is clear: exercise provides the stimulus, but recovery is when the adaptation happens. Your muscles repair micro-tears, energy stores replenish, and the nervous system resets during downtime. Without adequate recovery, you risk overtraining syndrome, which leads to chronic fatigue, decreased performance, and injury.

                            Sleep is the undisputed king of recovery. During deep sleep, the body releases growth hormone, which is essential for tissue repair. Aim for 7-9 hours of quality sleep per night. Nutrition also plays a role; adequate protein intake is vital for rebuilding muscle.

                            Active recovery is another great tool. Low-intensity movement like walking, swimming, or yoga stimulates blood flow, which helps flush out metabolic waste products like lactate without placing further stress on the body. Foam rolling and massage can also help alleviate muscle tightness. Listen to your body—if you are feeling run down, taking an extra rest day is often the most productive thing you can do for your long-term progress. Train hard, but recover harder.
                        """.trimIndent(),
                        imageUrl =
                            "https://images.unsplash.com/photo-1512438248247-f0f2a5a8b7f0?auto=format&fit=crop&w=800&q=80",
                        category = "Recovery"
                    )
                )
            articleDao.insertArticles(articles)
        }
    }
}

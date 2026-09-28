You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-contructor/codex-constructor-nextapps/AGENTS.md
Use this screen spec: /home/codex-contructor/codex-constructor-nextapps/screens-sport.md
Project directory: /tmp/apex-caster

Task metadata:
- Asana task gid: 1218927773183472
- Asana task name: Unique| 1.1 Constructor [USA Gmail C8] Apex Casino
- Asana URL: https://app.asana.com/1/1208304498069546/project/1213942668689495/task/1218927773183472
- Source Asana task gid: 1213488511461945
- Source Asana URL: https://app.asana.com/1/1208304498069546/project/1213598919743896/task/1213488511461945?focus=true
- App name: Apex Caster
- Company: Bitter Lake Retail LLC
- Domain: http://bitterlaakeretail.info
- Industry: Bitter Lake Retail redefines convenience in retail
- Current-task app theme: Бренд приложения — Bitter Lake Retail LLC.
Приложение для отслеживания активности содержит:
Планы активности: подбор тематических тренировок и ежедневных целей.
Трекер прогресса: запись выполненных активностей, времени и ключевых показателей.
История и аналитика: календарь занятий, серии активности и динамика результатов.
Логика взаимодействия:
Пользователь выбирает активность, запускает сессию и сохраняет результат после завершения.
Подтверждение: итоговый экран показывает показатели сессии и обновлённый прогресс.
Настройки приложения содержат:
Название компании: Bitter Lake Retail LLC.
Версию приложения.
Раздел Customer Support со ссылкой на сайт компании.
- Package: bitter.apexcaster.info
- Prefix: OCEBV
- Template: sport-tracker
- Source business context: Специфика компании — розничная торговля товарами повседневного спроса: бытовая химия, личная гигиена, снеки и напитки, товары для дома. Приложение по продаже товаров компании, содержит список всех товаров (с возможностью сортировки по категориям: уход, еда, дом и др.). История покупок. Корзина товаров с формой бронирования заказа. После подтверждения бронирования пользователь видит баннер с информацией о номере и деталях заказа с уведомлением о том, что его ожидают в магазине в течение 24 часов. Настройки должны содержать информацию о: названии компании, версии приложения, линку Customers Support со ссылкой на сайт компании.
(можно разнообразить главную страницу каруселью с акциями, новинками или лайфхаками по организации быта)
- Optional app concept override: None — synthesize the concept automatically

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Create a new-app concept using the Concept Synthesis rules in the orchestrator AGENTS.md. The template defines functionality; source-task text is business context only. Save the normalized brief to /tmp/apex-caster/concept-brief.md and `app_concept` in .builder-state.json before implementing.
3. Do not create project-local agent instruction files inside /tmp/apex-caster.
4. Implement all required screens/content/data/assets/icon according to that concept, the orchestrator AGENTS.md, and the screen spec.
5. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
6. Do not push to GitHub, do not update Asana, and do not send Slack.
7. Do not run Gradle, assembleDebug, build-debug.sh, or any compilation task. The autonomous flow runs only quality-check.sh; a developer performs compilation separately after publication.

package bitter.apexcaster.info.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import bitter.apexcaster.info.ui.viewmodel.OCEBVArticleDetailViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVBookingViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVHistoryViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVHomeViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVOnboardingVM
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVSplashVM
import bitter.apexcaster.info.ui.viewmodel.OCEBVStatisticsViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVWorkoutSessionViewModel

/**
 * The presentation layer: one entry per ViewModel, resolved by `koinViewModel()` in screens.
 *
 * Their dependencies come from [dataModule], so that module must be loaded alongside this one.
 * A ViewModel taking a `SavedStateHandle` (e.g. [OCEBVArticleDetailViewModel], for nav arguments)
 * declares it as a plain `get()` — Koin supplies it from the ViewModel's CreationExtras. Do not
 * turn these into parameterised factories; screens call `koinViewModel()` without parameters,
 * and a required parameter would fail at resolution time.
 */
val viewModule = module {
    viewModel { OCEBVOCEBVSplashVM(get()) }
    viewModel { OCEBVOCEBVOnboardingVM(get()) }
    viewModel { OCEBVHomeViewModel(get(), get()) }
    viewModel { OCEBVArticleDetailViewModel(get(), get()) }
    viewModel { OCEBVBookingViewModel(get()) }
    viewModel { OCEBVHistoryViewModel(get()) }
    viewModel { OCEBVWorkoutSessionViewModel(get()) }
    viewModel { OCEBVStatisticsViewModel(get()) }
}

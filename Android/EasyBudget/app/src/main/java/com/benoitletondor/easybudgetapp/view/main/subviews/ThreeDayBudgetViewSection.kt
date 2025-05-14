/*
 *   Copyright 2025 Benoit Letondor
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.benoitletondor.easybudgetapp.view.main.subviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benoitletondor.easybudgetapp.view.main.MainViewModel
import com.benoitletondor.easybudgetapp.view.main.ThreeDayBudgetState
import com.benoitletondor.easybudgetapp.view.main.ThreeDayBudgetStatusView
import kotlinx.coroutines.flow.StateFlow
import java.util.Currency

@Composable
fun ThreeDayBudgetViewSection(
    threeDayBudgetStateFlow: StateFlow<ThreeDayBudgetState>,
    userCurrencyFlow: StateFlow<Currency>,
    onRetryButtonClicked: () -> Unit
) {
    val budgetState by threeDayBudgetStateFlow.collectAsState()
    val currency by userCurrencyFlow.collectAsState()

    // Only show the budget section if the feature is enabled
    if (budgetState is ThreeDayBudgetState.Disabled) {
        return
    }

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        ThreeDayBudgetStatusView(
            state = budgetState,
            currency = currency
        )
    }
}

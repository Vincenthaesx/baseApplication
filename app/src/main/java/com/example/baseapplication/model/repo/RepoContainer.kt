package com.example.baseapplication.model.repo

import com.example.baseapplication.model.repo.repoImplementations.MainManager

interface RepoContainer {
    val mainRepo: MainManager
}


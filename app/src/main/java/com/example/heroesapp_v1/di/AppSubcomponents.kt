package com.example.heroesapp_v1.di

import dagger.Module

@Module(subcomponents = [HeroComponent::class, HeroesComponent::class])
class AppSubcomponents
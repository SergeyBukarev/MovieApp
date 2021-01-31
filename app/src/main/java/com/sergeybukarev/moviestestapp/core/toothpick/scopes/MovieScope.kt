package com.sergeybukarev.moviestestapp.core.toothpick.scopes

import com.sergeybukarev.domain.toothpick.qualifiers.MovieId
import com.sergeybukarev.moviestestapp.core.toothpick.modules.MovieDetailsModule
import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeModulesFactory
import toothpick.config.Module
import javax.inject.Scope

@Scope
@Target(AnnotationTarget.CLASS)
annotation class MovieScope

@Parcelize
class MovieScopeArguments(@MovieId val movieId: Int) : ScopeModulesFactory() {
    override fun createModules(scope: toothpick.Scope): Array<Module> {
        return arrayOf(
            MovieDetailsModule(movieId),
        )
    }
}

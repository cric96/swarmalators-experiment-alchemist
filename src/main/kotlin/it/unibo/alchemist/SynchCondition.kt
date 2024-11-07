package it.unibo.alchemist

import it.unibo.alchemist.model.*
import it.unibo.alchemist.model.conditions.AbstractCondition
import it.unibo.alchemist.model.molecules.SimpleMolecule
import it.unibo.alchemist.model.timedistributions.DiracComb
import org.danilopianini.util.ListSet

class SynchCondition<T>(node: Node<T>, val molecule: String): AbstractCondition<T>(node) {
    override fun getContext(): Context = Context.NEIGHBORHOOD

    override fun getPropensityContribution(): Double = 0.0

    override fun isValid(): Boolean {
        val shouldFire = node.getConcentration(SimpleMolecule(molecule)) as Boolean
        @Suppress("UNCHECKED_CAST")
        node.setConcentration(SimpleMolecule(molecule), false as T)
        return shouldFire
    }
}
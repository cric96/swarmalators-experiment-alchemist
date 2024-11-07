package it.unibo.alchemist

import it.unibo.alchemist.model.Action
import it.unibo.alchemist.model.Context
import it.unibo.alchemist.model.Environment
import it.unibo.alchemist.model.Node
import it.unibo.alchemist.model.Reaction
import it.unibo.alchemist.model.actions.AbstractAction
import it.unibo.alchemist.model.molecules.SimpleMolecule

class SimpleSwapAction<T>(node: Node<T>, val environment: Environment<T, *>): AbstractAction<T>(node) {
    override fun cloneAction(node: Node<T>?, reaction: Reaction<T>?): Action<T> =
        SimpleSwapAction(node!!, environment)

    override fun execute() {
        @Suppress("UNCHECKED_CAST")
        val time = environment.getSimulation().getTime().toDouble()
        val floatingPart = time - time.toInt()
        val nodes = environment.getNeighborhood(node)
        val phases = nodes.neighbors.toList()
            .map { it.getConcentration(SimpleMolecule("phase")) }
            .mapNotNull { it as Double }
            .filter { it > 0 }
            .map { it * Math.PI }
        if (phases.isNotEmpty()) {
            val newPhase = phases.map { Math.sin(it - floatingPart) }.sum() / phases.size
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("phase"), newPhase as T)
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("swapTime"), floatingPart as T)
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("swap"), true as T)
        } else {
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("phase"), floatingPart as T)
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("swapTime"), 0.0 as T)
            @Suppress("UNCHECKED_CAST")
            node.setConcentration(SimpleMolecule("swap"), true as T)

        }
    }

    override fun getContext(): Context = Context.NEIGHBORHOOD
}
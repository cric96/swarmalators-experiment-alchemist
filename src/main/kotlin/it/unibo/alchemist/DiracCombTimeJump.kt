package it.unibo.alchemist

import it.unibo.alchemist.model.Environment
import it.unibo.alchemist.model.Node
import it.unibo.alchemist.model.Time
import it.unibo.alchemist.model.molecules.SimpleMolecule
import it.unibo.alchemist.model.timedistributions.AbstractDistribution
import it.unibo.alchemist.model.timedistributions.DiracComb
import it.unibo.alchemist.model.times.DoubleTime
import org.apache.commons.math3.random.RandomGenerator

class DiracCombTimeJump<T>(
    val node: Node<T>,
    val rateDistribution: Double,
    val molecule: String,
    val randomGenerator: RandomGenerator,
    ): AbstractDistribution<T>(DoubleTime(randomGenerator.nextDouble())) {
    override fun getRate(): Double = rateDistribution

    override fun updateStatus(currentTime: Time?, executed: Boolean, param: Double, environment: Environment<T, *>?) {
        val jump = node.getConcentration(SimpleMolecule(molecule)) as Double
        if (executed) {
            setNextOccurrence(DoubleTime(currentTime!!.toDouble() + (1.0 / rate) + jump))
        }
    }

    override fun cloneOnNewNode(destination: Node<T>, currentTime: Time): AbstractDistribution<T> =
        DiracCombTimeJump(destination, rate, molecule, randomGenerator)

}
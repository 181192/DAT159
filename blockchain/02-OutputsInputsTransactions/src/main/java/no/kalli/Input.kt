package no.kalli

/**
 * This class represents a [Input] to a [Transaction] and takes the
 * hash from the previous transaction [prevTxHash] and the previous
 * [Output] index [prevOutputIndex].
 */
data class Input(val prevTxHash: String, val prevOutputIndex: Int)

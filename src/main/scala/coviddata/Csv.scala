package coviddata

/**
 * The simplest CSV handling class in the world. Treats all values
 * as Strings (does not do any implicit conversions for you). Essentially,
 * just creates a giant nxn matrix of csv file for in memory lookups. It is
 * up to the user to understand the csv file they are parsing and handle
 * type conversions.
 * @param {[type]}  contents:   String  [description]
 * @param {Boolean} dropHeader: Boolean [description]
 * @param {String}  delim:      String  [description]
 */
class Csv(contents: String, dropHeader: Boolean = false, delim: String = ",") {
    
    // Upon construction, do the following:
    // 1. split at the newline char
    // 2. filter out first line if dropHeader specified
    // 3. split at delimiter and return seq for each row
    // 4. convert row array to seq
    val data: Seq[Seq[String]] = contents
        .split("\n")
        .zipWithIndex
        .filter {
            case (el, i) => if (dropHeader && i == 0) false else true
        }
        .map {
            case (el, i) => el.split(delim).toSeq
        }
        .toSeq

    /**
     * Gets rows where column equals a certain value
     * @type {[type]}
     */
    def getRowsWhereColEqual(value: String, colIndex: Int): Seq[Seq[String]] = {
        data.filter(row => row(colIndex) == value)
    }

    /**
     * Gets single cols values for all row as one seq
     */
    def getColVals(colIndex: Int): Seq[String] = {
        data.map(row => row(colIndex))
    }

    /**
     * Helper function to convert each row to an obj of type T
     * given a mapping function to do so
     * @type {Function}
     */
    def mapValsToObjs[T](mappingFunc: Seq[String] => T): Seq[T] = {
        data.map(mappingFunc)
    }
}

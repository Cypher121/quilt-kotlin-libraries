import java.util.regex.Matcher
import java.util.regex.Pattern

public class MinecraftVersion(public val version: String, public val versionEdition: String? = null) {

    public val isSnapshot: Boolean
        get() = SNAPSHOT_PATTERN.matcher(this.version).matches()
    public val semVer: String
        get() {
            var matcher: Matcher
            return if (SNAPSHOT_PATTERN.matcher(this.version).also { matcher = it }
                    .matches()) {
                this.versionEdition + String.format(
                    "-alpha.%s.%s.%s",
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3)
                )
            } else if (PRE_RELEASE_PATTERN.matcher(this.version).also { matcher = it }
                    .matches()) {
                this.version.substring(0, this.version.indexOf("-") + 1) + String.format("beta.%s", matcher.group(1))
            } else if (RELEASE_CANDIDATE_PATTERN.matcher(this.version)
                    .also { matcher = it }.matches()
            ) {
                this.version.substring(0, this.version.indexOf("-") + 1) + String.format("rc.%s", matcher.group(1))
            } else if (RELEASE_PATTERN.matcher(this.version).matches()) {
                this.version
            } else {
                throw IllegalArgumentException("Cannot give a semver-compliant string for version " + this.version)
            }
        }
    public val fancyString: String
        get() {
            if (isSnapshot) {
                return this.version
            }
            val version: Array<String> =
                this.version.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var index: Int
            if (version[1].indexOf("-pre").also { index = it } != -1 || version[1].indexOf("-rc")
                    .also { index = it } != -1) {
                version[1] = version[1].substring(0, index)
            }
            return version[0] + '.' + version[1]
        }

    private companion object {
        private val RELEASE_PATTERN = Pattern.compile("\\d+\\.\\d+(\\.\\d+)?$")
        private val PRE_RELEASE_PATTERN = Pattern.compile(".+(?:-pre| Pre-[Rr]elease )(\\d+)$")
        private val RELEASE_CANDIDATE_PATTERN = Pattern.compile(".+(?:-rc| [Rr]elease Candidate )(\\d+)$")
        private val SNAPSHOT_PATTERN = Pattern.compile("(?:Snapshot )?(\\d+)w0?(0|[1-9]\\d*)([a-z])$")
        private val EXPERIMENTAL_PATTERN = Pattern.compile("(?:.*[Ee]xperimental [Ss]napshot )(\\d+)")
    }
}
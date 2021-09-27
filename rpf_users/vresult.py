class VResult:
    """Validation result object."""

    def __init__(self, reason=None):
        self.reason = reason
        self.is_valid = len(reason or '') == 0

import httpx
import os
from fastapi import HTTPException

RECAPTCHA_SECRET_KEY = os.getenv("RECAPTCHA_SECRET_KEY")
RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify"
MIN_SCORE = 0.5


async def verify_recaptcha(token: str, action: str = None) -> float:
    if not RECAPTCHA_SECRET_KEY:
        raise RuntimeError("RECAPTCHA_SECRET_KEY environment variable is not set")

    async with httpx.AsyncClient() as client:
        response = await client.post(
            RECAPTCHA_VERIFY_URL,
            data={"secret": RECAPTCHA_SECRET_KEY, "response": token},
        )

    result = response.json()

    if not result.get("success"):
        error_codes = result.get("error-codes", [])
        raise HTTPException(
            status_code=400,
            detail=f"reCAPTCHA verification failed: {error_codes}",
        )

    score: float = result.get("score", 0.0)

    if action and result.get("action") != action:
        raise HTTPException(
            status_code=400,
            detail="reCAPTCHA action mismatch",
        )

    if score < MIN_SCORE:
        raise HTTPException(
            status_code=400,
            detail=f"reCAPTCHA score too low ({score:.2f}). Submission rejected.",
        )

    return score
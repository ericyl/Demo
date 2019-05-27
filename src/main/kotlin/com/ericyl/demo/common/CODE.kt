package com.ericyl.demo.common

const val CODE_SUCCESS = 200
const val CODE_REQUEST_CREATED = 201
const val CODE_RESPONSE_NO_CONTENT = 204

const val CODE_SERVER_RESOURCE_NOT_MODIFIED = 304

const val CODE_BAD_REQUEST = 400
const val CODE_REQUEST_UNAUTHORIZED = 401
const val CODE_FORBIDDEN = 403
const val CODE_PAGE_NOT_FOUND = 404
const val CODE_REQUEST_CONFLICT = 409
const val CODE_USER_LOCKED = 410
const val CODE_CREDENTIALS_EXPIRED = 411

const val CODE_USER_HAS_BEEN_DISABLED = 412

const val CODE_NETWORK_UNREACHABLE = 499

const val CODE_INTERNAL_SERVER_ERROR = 500
const val CODE_SERVICE_UNAVAILABLE = 503

const val CODE_AN_UNKNOWN_ERROR = 10000


const val DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode"
const val DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode"

const val REDIS_PERMISSION_ADMIN_KEY = "hospitalmangament:permission:admin"
const val REDIS_ROLE_KEY = "hospitalmangament:tmp:role"
const val REDIS_ROLE_OPERATE_KEY = "hospitalmangament:tmp:role:operate"
const val REDIS_OPERATE_KEY = "hospitalmangament:tmp:operate"
const val REDIS_HOSPITAL_KEY = "hospitalmangament:tmp:hospital"
const val REDIS_TMP_INVOICE_RANGE_KEY = "hospitalmangament:tmp:invoice:range"
const val REDIS_INVOICE_RANGE_KEY = "hospitalmangament:tmp:invoicerange"
const val REDIS_INVOICE_NUMBER_KEY = "hospitalmangament:tmp:invoicenumber"
const val REDIS_TOKEN_KEY = "hospitalmangament:tmp:token"
const val REDIS_WRITE_OFF_LIST_KEY = "hospitalmangament:tmp:writeoff:list"
const val REDIS_PAPER_WRITE_OFF_LIST_KEY = "hospitalmangament:tmp:writeoff:paperlist"
const val REDIS_VERIFY_LIST_KEY = "hospitalmangament:tmp:verify:list"
const val REDIS_PAPER_INVOICE_RANGE_KEY = "hospitalmangament:tmp:paperinvoicerange"
const val REDIS_VALIDATE_IMAGE_CODE_KEY = "hospitalmangament:tmp:vaildate:code:image"
const val REDIS_VALIDATE_SMS_CODE_KEY = "hospitalmangament:tmp:vaildate:code:sms"
